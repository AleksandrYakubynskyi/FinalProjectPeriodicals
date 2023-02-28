package com.project.dao.impl;

import com.project.connection.DbManager;
import com.project.constant.AttributeNameConstant;
import com.project.dao.PublicationDao;
import com.project.entity.Publication;
import com.project.entity.User;
import com.project.entity.enums.Topic;
import com.project.util.QueryBuilder;
import com.project.util.SqlFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DefaultPublicationDao implements PublicationDao {

    public static final Logger LOGGER = Logger.getLogger(DefaultPublicationDao.class);
    private final DataSource dataSource;

    public DefaultPublicationDao() {
        dataSource = DbManager.getDataSource();
    }

    @Override
    public List<Publication> getAllPublications(Map<String, String[]> paramMap) {
        String query = SqlFactory.getFactory()
                .selectFrom(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .build();

        query += QueryBuilder.addQuery(paramMap);
        query += QueryBuilder.addLimitsAndSort(paramMap);

        List<Publication> publications = new LinkedList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                publications.add(getPublicationFromResultSet(resultSet));
            }
            LOGGER.info("Publication returned successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot get publication" + ExceptionUtils.getMessage(e));
        }

        return publications;
    }

    @Override
    public void addPublication(Publication publication) {
        String query = SqlFactory.getFactory()
                .insertInto(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .parameters(AttributeNameConstant.ID_ATTRIBUTE, AttributeNameConstant.TOPIC_ATTRIBUTE,
                        AttributeNameConstant.NAME_ATTRIBUTE, AttributeNameConstant.PRICE_ATTRIBUTE,
                        AttributeNameConstant.CONTENT_ATTRIBUTE)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int parameterIndex = NumberUtils.INTEGER_ZERO;
            preparedStatement.setString(++parameterIndex, publication.getId());
            preparedStatement.setString(++parameterIndex, String.valueOf(publication.getTopic()));
            preparedStatement.setString(++parameterIndex, publication.getName());
            preparedStatement.setBigDecimal(++parameterIndex, publication.getPrice());
            preparedStatement.setString(++parameterIndex, publication.getContent());
            preparedStatement.executeUpdate();
            LOGGER.info("Publication added successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot add publication" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public Optional<Publication> getPublicationById(String id) {
        String query = SqlFactory.getFactory()
                .selectFrom(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .where(AttributeNameConstant.ID_ATTRIBUTE)
                .equals(id)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                LOGGER.info("Publication returned successfully");
                return Optional.of(getPublicationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get publication" + ExceptionUtils.getMessage(e));
        }
        return Optional.empty();
    }

    @Override
    public void setPublicationForUser(User user, Publication publication) {
        String query = SqlFactory.getFactory()
                .insertInto(AttributeNameConstant.USER_HAS_PUBLICATION)
                .parameters(AttributeNameConstant.USER_ID_ATTRIBUTE, AttributeNameConstant.PUBLICATION_ID_ATTRIBUTE)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(NumberUtils.INTEGER_ONE, user.getId());
            preparedStatement.setString(NumberUtils.INTEGER_TWO, publication.getId());
            preparedStatement.executeUpdate();
            LOGGER.info("Subscription added successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot set subscription" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public void updatePublication(Publication publication) {
        String query = SqlFactory.getFactory()
                .update(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .set(AttributeNameConstant.TOPIC_ATTRIBUTE, AttributeNameConstant.NAME_ATTRIBUTE,
                        AttributeNameConstant.PRICE_ATTRIBUTE, AttributeNameConstant.CONTENT_ATTRIBUTE,
                        AttributeNameConstant.ID_ATTRIBUTE)
                .where(AttributeNameConstant.ID_ATTRIBUTE)
                .equals(publication.getId())
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int parameterIndex = NumberUtils.INTEGER_ZERO;
            preparedStatement.setString(++parameterIndex, String.valueOf(publication.getTopic()));
            preparedStatement.setString(++parameterIndex, publication.getName());
            preparedStatement.setBigDecimal(++parameterIndex, publication.getPrice());
            preparedStatement.setString(++parameterIndex, publication.getContent());
            preparedStatement.setString(++parameterIndex, publication.getId());
            preparedStatement.executeUpdate();
            LOGGER.info("Publication updated successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot update publication" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public void removePublication(String id) {
        String query = SqlFactory.getFactory()
                .deleteFrom(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .where(AttributeNameConstant.ID_ATTRIBUTE)
                .equals(id)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Publication deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot delete publication" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public void removePublicationForUser(String publicationId, String userId) {
        String query = SqlFactory.getFactory()
                .deleteFrom(AttributeNameConstant.USER_HAS_PUBLICATION)
                .where(AttributeNameConstant.PUBLICATION_ID_ATTRIBUTE)
                .equals(publicationId)
                .and(AttributeNameConstant.USER_ID_ATTRIBUTE)
                .equals(userId)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Subscription deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot delete subscription" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public Optional<Publication> searchPublicationByName(String name) {
        String query = SqlFactory.getFactory()
                .selectFrom(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .where(AttributeNameConstant.NAME_ATTRIBUTE)
                .like(name)
                .build();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getPublicationFromResultSet(resultSet));
            }
            LOGGER.info("Publication successfully found");
        } catch (SQLException e) {
            LOGGER.error("Cannot found publication" + ExceptionUtils.getMessage(e));
        }
        return Optional.empty();
    }

    @Override
    public List<Publication> getPublicationForUser(String id) {
        String query = SqlFactory.getFactory()
                .selectFields("p.id, p.topic, p.name, p.content, p.price")
                .from("publication p")
                .join("user_has_publication r")
                .on("p.id = r.publication_id")
                .where("r.user_id")
                .equals(id)
                .build();

        List<Publication> publications = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                publications.add(getPublicationFromResultSet(resultSet));
            }
            LOGGER.info("Subscription returned successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot get subscription" + ExceptionUtils.getMessage(e));
        }
        return publications;
    }

    @Override
    public List<Publication> getPublicationForUserWithParams(String id, Map<String, String[]> paramMap) {
        String query = SqlFactory.getFactory()
                .selectFields("p.id, p.topic, p.name, p.content, p.price")
                .from("publication p")
                .join("user_has_publication r")
                .on("p.id = r.publication_id")
                .where("r.user_id")
                .equals(id)
                .build();

        query += QueryBuilder.addQuery(paramMap);
        query += QueryBuilder.addLimitsAndSort(paramMap);

        List<Publication> publications = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                publications.add(getPublicationFromResultSet(resultSet));
            }
            LOGGER.info("Subscription returned successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot get subscription" + ExceptionUtils.getMessage(e));
        }
        return publications;
    }

    @Override
    public int getNumberOfPublication(Map<String, String[]> paramMap) {
        String query = SqlFactory.getFactory()
                .selectCountFrom(AttributeNameConstant.PUBLICATION_ATTRIBUTE)
                .build();
        query += QueryBuilder.addQuery(paramMap);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(NumberUtils.INTEGER_ONE);
        } catch (SQLException e) {
            LOGGER.error("Can not get number of publication" + ExceptionUtils.getMessage(e));
        }
        return NumberUtils.INTEGER_ZERO;
    }

    @Override
    public int getNumberOfPublicationForUser(User user) {
        String query = SqlFactory.getFactory()
                .selectCountFrom("publication p")
                .join("user_has_publication r")
                .on("p.id = r.publication_id")
                .where("r.user_id")
                .equals(user.getId())
                .build();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(NumberUtils.INTEGER_ONE);
        } catch (SQLException e) {
            LOGGER.error("Cannot get number of publication" + ExceptionUtils.getMessage(e));
        }
        return NumberUtils.INTEGER_ZERO;
    }

    private Publication getPublicationFromResultSet(ResultSet resultSet) throws SQLException {
        Publication publication = new Publication();

        publication.setId(resultSet.getString(AttributeNameConstant.ID_ATTRIBUTE));
        publication.setTopic(Topic.valueOf(resultSet.getString(AttributeNameConstant.TOPIC_ATTRIBUTE)));
        publication.setName(resultSet.getString(AttributeNameConstant.NAME_ATTRIBUTE));
        publication.setPrice(resultSet.getBigDecimal(AttributeNameConstant.PRICE_ATTRIBUTE));
        publication.setContent(resultSet.getString(AttributeNameConstant.CONTENT_ATTRIBUTE));

        return publication;
    }
}