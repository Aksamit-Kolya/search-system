package com.anika.core.repository;

import com.anika.core.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {


//    @Query(
//            value =
//            "SELECT " +
//                    "url," +
//                    "description," +
//                    "title " +
//            "FROM page\n" +
//            "WHERE make_tsvector(" +
//                    "key_words, " +
//                    "description, " +
//                    "title, " +
//                    "content" +
//            ") " +
//            "@@ to_tsquery('english', :searchQuery) " +
//            "ORDER BY ts_rank(" +
//                "make_tsvector(" +
//                    "key_words, " +
//                    "description, " +
//                    "title, " +
//                    "content" +
//                "), " +
//                "to_tsquery('english', :searchQuery))" +
//            "DESC",
//            nativeQuery = true
//    )
//    List<Page> findAll();
}
