package com.project.dao;

import com.project.entity.Publication;
import com.project.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PublicationDao {
    void addPublication(Publication publication);

    Optional<Publication> getPublicationById(String id);

    Optional<Publication> searchPublicationByName(String name);

    void setPublicationForUser(User user, Publication publication);

    List<Publication> getPublicationForUser(String id);

    List<Publication> getPublicationForUserWithParams(String id, Map<String, String[]> paramMap);

    void removePublication(String id);

    void removePublicationForUser(String publication_id, String user_id);

    void updatePublication(Publication publication);

    int getNumberOfPublication(Map<String, String[]> paramMap);

    List<Publication> getAllPublications(Map<String, String[]> paramMap);

    int getNumberOfPublicationForUser(User user);
}
