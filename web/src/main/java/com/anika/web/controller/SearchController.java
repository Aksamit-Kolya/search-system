package com.anika.web.controller;

import com.anika.core.entity.Query;
import com.anika.core.entity.User;
import com.anika.core.entity.UserQuery;
import com.anika.core.repository.QueryRepository;
import com.anika.core.repository.UserQueryRepository;
import com.anika.core.service.DocumentServiceImpl;
import com.anika.web.dto.DocumentDTO;
import com.anika.web.dto.QueryDTO;
import com.anika.web.mapper.DocumentMapper;
import com.anika.web.mapper.QueryMapper;
import com.anika.web.service.SearchService;
import com.anika.web.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final DocumentServiceImpl documentService;
    private final UserService userService;
    @Autowired
    public SearchController(SearchService searchService, DocumentServiceImpl documentService, UserService userService) {
        this.searchService = searchService;
        this.documentService = documentService;
        this.userService = userService;
    }

    //@CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/documents")
    public List<DocumentDTO> findDocumentsByKeywords(@RequestParam("query") String query) {
        // Get the currently authenticated user's details from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the user's principal object, which contains their details
            Object principal = authentication.getPrincipal();

            // Check if the principal object is an instance of UserDetails
            if (principal instanceof UserDetails userDetails) {

                // Get the user's username, authorities, etc.
                String username = userDetails.getUsername();

                Optional<User> user = userService.findUserByName(username);

                user.ifPresent(value -> userService.saveUserQuery(value, query));
            }
        }
        return Mappers.getMapper(DocumentMapper.class).toDTOList(searchService.searchDocuments(query));
    }

    @PostMapping("/TF-ITF/recalculate")
    public ResponseEntity<String> sendDomainToCrawler() {
        documentService.recalculateTfItfRankForAllDocumentKeywords();
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/history")
    public List<QueryDTO> getUserHistory() {
        // Get the currently authenticated user's details from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the user's principal object, which contains their details
            Object principal = authentication.getPrincipal();

            // Check if the principal object is an instance of UserDetails
            if (principal instanceof UserDetails userDetails) {

                // Get the user's username, authorities, etc.
                String username = userDetails.getUsername();

                Optional<User> user = userService.findUserByName(username);

                if(user.isPresent()) {
                    List<QueryDTO> queries = Mappers.getMapper(QueryMapper.class).toDTOList(searchService.getUserHistory(user.get()));
                    return queries.stream()
                            .map(QueryDTO::text)
                            .distinct()
                            .map(queryText -> queries.stream().filter(query -> query.text().equals(queryText)).findFirst().get())
                            .collect(Collectors.toList());
                }
            }
        }
        return null;
        //return Mappers.getMapper(QueryMapper.class).toDTOList(queryRepository.findAllByUserIdSortedByTimestamp(userService.findUserByName("test").get().getId())) ;
        //return userQueryRepository.findAllByUser(userService.findUserByName("test").get()).stream().map((userQuery -> userQuery.getId())).collect(Collectors.toList());
    }
    @Autowired
    private QueryRepository queryRepository;
    @Autowired
    private UserQueryRepository userQueryRepository;
}
