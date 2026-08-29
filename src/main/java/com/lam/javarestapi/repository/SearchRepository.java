package com.lam.javarestapi.repository;

import com.lam.javarestapi.dto.response.PageResponse;
import com.lam.javarestapi.model.Address;
import com.lam.javarestapi.model.User;
import com.lam.javarestapi.repository.criteria.AddressCriteria;
import com.lam.javarestapi.repository.criteria.SearchCriteria;
import com.lam.javarestapi.repository.criteria.UserSearchCriteriaConsumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getAllUsersWithSortByWithColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        StringBuilder sqlQuery = new StringBuilder("select new com.lam.javarestapi.dto.response.UserDetailResponse(u.id,u.firstName,u.lastName,u.email,u.phone) from User u where 1=1");


        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" and lower(u.firstName) like(:firstName)");
            sqlQuery.append(" or lower(u.lastName) like(:lastName)");
            sqlQuery.append(" or lower(u.email) like(:email)");
        }
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find())
                sqlQuery.append(String.format(" order by u.%s %s", matcher.group(1), matcher.group(3)));
        }
        Query selectorQuery = entityManager.createQuery(sqlQuery.toString());
        selectorQuery.setFirstResult(pageNo);
        selectorQuery.setMaxResults(pageSize);

        if (StringUtils.hasLength(search)) {
            selectorQuery.setParameter("firstName", String.format("%%%s%%", search));
            selectorQuery.setParameter("lastName", String.format("%%%s%%", search));
            selectorQuery.setParameter("email", String.format("%%%s%%", search));
        }

        List users = selectorQuery.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("select count(*) from User u");


        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" and lower(u.firstName) like(?1)");
            sqlCountQuery.append(" or lower(u.lastName) like(:?2)");
            sqlCountQuery.append(" or lower(u.email) like(?3)");
        }
        Query selectorCountQuery = entityManager.createQuery(sqlCountQuery.toString());

        if (StringUtils.hasLength(search)) {
            selectorCountQuery.setParameter(1, String.format("%%%s%%", search));
            selectorCountQuery.setParameter(2, String.format("%%%s%%", search));
            selectorCountQuery.setParameter(3, String.format("%%%s%%", search));
        }

        Long totalElement = (Long) selectorCountQuery.getSingleResult();
        Page<?> page = new PageImpl<>(users, PageRequest.of(pageNo, pageSize), totalElement);
        System.out.println(users);
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(page.getTotalPages())
                .items(page.stream().toList())
                .build();


    }

    public PageResponse<?> advanceSearchUser(int pageNo, int pageSize, String sortBy, List<String> address, String... search) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        List<AddressCriteria> addressCriteriaList = new ArrayList<>();
        if (search != null) {

            for (String s : search) {
                Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(.*)");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    searchCriteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }

            }

        }
        if (address != null) {
            for (String a : address) {
                Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    addressCriteriaList.add(new AddressCriteria(matcher.group(1), matcher.group(3)));
                }

            }
        }

        List<User> users = getUser(pageNo, pageSize, searchCriteriaList, addressCriteriaList, sortBy);
        Long totalElements = getTotalElements(searchCriteriaList, addressCriteriaList);
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(totalElements.intValue())
                .items(users)
                .build();
    }


    private List<User> getUser(int pageNo, int pageSize, List<SearchCriteria> searchCriteriaList, List<AddressCriteria> address, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        // xu ly cac dieu kien tim kiem
        Predicate predicate = criteriaBuilder.conjunction();

        UserSearchCriteriaConsumer queryConsumer = new UserSearchCriteriaConsumer(criteriaBuilder, predicate, root);
        if (address != null) {
            Join<User, Address> addressUserJoin = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.conjunction();
            for (AddressCriteria x : address) {
                addressPredicate = criteriaBuilder.and(predicate, criteriaBuilder.like(addressUserJoin.get(x.getKey()), "%" + x.getValue() + "%"));
            }
            query.where(predicate, addressPredicate);
        } else {
            searchCriteriaList.forEach(queryConsumer);
            predicate = queryConsumer.getPredicate();
            query.where(predicate);
        }


        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));

                }
            }


        }

        return entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
    }

    private Long getTotalElements(List<SearchCriteria> searchCriteriaList, List<AddressCriteria> address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        // xu ly cac dieu kien tim kiem
        Predicate predicate = criteriaBuilder.conjunction();

        UserSearchCriteriaConsumer queryConsumer = new UserSearchCriteriaConsumer(criteriaBuilder, predicate, root);
        if (address != null) {
            Join<User, Address> addressUserJoin = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.conjunction();
            for (AddressCriteria x : address) {
                addressPredicate = criteriaBuilder.and(predicate, criteriaBuilder.like(addressUserJoin.get(x.getKey()), "%" + x.getValue() + "%"));
            }
            query.select(criteriaBuilder.count(root));

            query.where(predicate, addressPredicate);
        } else {
            searchCriteriaList.forEach(queryConsumer);
           
            query.select(criteriaBuilder.count(root));
        }
        return entityManager.createQuery(query).getSingleResult();

    }

}
