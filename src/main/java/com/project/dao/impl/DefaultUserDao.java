package com.project.dao.impl;

import com.project.connection.DbManager;
import com.project.constant.AttributeNameConstant;
import com.project.dao.UserDao;
import com.project.entity.User;
import com.project.entity.enums.Gender;
import com.project.entity.enums.Role;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultUserDao implements UserDao {
    public static final Logger LOGGER = Logger.getLogger(DefaultUserDao.class);
    private final DataSource dataSource;

    public DefaultUserDao() {
        dataSource = DbManager.getDataSource();
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getString(AttributeNameConstant.ID_ATTRIBUTE));
        user.setFirstname(resultSet.getString(AttributeNameConstant.FIRSTNAME_ATTRIBUTE));
        user.setLastname(resultSet.getString(AttributeNameConstant.LASTNAME_ATTRIBUTE));
        user.setEmail(resultSet.getString(AttributeNameConstant.EMAIL_ATTRIBUTE));
        user.setPassword(resultSet.getString(AttributeNameConstant.PASSWORD_ATTRIBUTE));
        user.setGender(Gender.valueOf(resultSet.getString(AttributeNameConstant.GENDER_ATTRIBUTE)));
        user.setRole(Role.valueOf(resultSet.getString(AttributeNameConstant.ROLE_ATTRIBUTE)));
        user.setBalance(resultSet.getBigDecimal(AttributeNameConstant.BALANCE_ATTRIBUTE));

        return user;
    }

    @Override
    public List<User> getAllUsers(Map<String, String[]> paramMap) {
        String query = SqlFactory.getFactory()
                .selectFrom(AttributeNameConstant.USER_ATTRIBUTE)
                .build();

        query += QueryBuilder.addQuery(paramMap);
        query += QueryBuilder.addLimitsAndSort(paramMap);

        List<User> users = new LinkedList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public int getNumberOfUser(Map<String, String[]> paramMap) {
        String query = SqlFactory.getFactory()
                .selectCountFrom(AttributeNameConstant.USER_ATTRIBUTE)
                .build();
        query += QueryBuilder.addQuery(paramMap);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            LOGGER.info("Number of user returned successfully");
            return resultSet.getInt(NumberUtils.INTEGER_ONE);
        } catch (SQLException e) {
            LOGGER.error("Cannot get number of user" + ExceptionUtils.getMessage(e));
        }
        return NumberUtils.INTEGER_ZERO;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String query = SqlFactory.getFactory()
                .selectFrom(AttributeNameConstant.USER_ATTRIBUTE)
                .where(AttributeNameConstant.EMAIL_ATTRIBUTE)
                .equals(email)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            LOGGER.info("User returned successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot get user" + ExceptionUtils.getMessage(e));
        }

        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {
        String query = SqlFactory.getFactory()
                .update(AttributeNameConstant.USER_ATTRIBUTE)
                .set(AttributeNameConstant.FIRSTNAME_ATTRIBUTE, AttributeNameConstant.LASTNAME_ATTRIBUTE,
                        AttributeNameConstant.EMAIL_ATTRIBUTE, AttributeNameConstant.PASSWORD_ATTRIBUTE,
                        AttributeNameConstant.GENDER_ATTRIBUTE, AttributeNameConstant.ROLE_ATTRIBUTE,
                        AttributeNameConstant.BALANCE_ATTRIBUTE, AttributeNameConstant.ID_ATTRIBUTE)
                .where(AttributeNameConstant.ID_ATTRIBUTE)
                .equals(user.getId())
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int parameterIndex = NumberUtils.INTEGER_ZERO;

            preparedStatement.setString(++parameterIndex, user.getFirstname());
            preparedStatement.setString(++parameterIndex, user.getLastname());
            preparedStatement.setString(++parameterIndex, user.getEmail());
            preparedStatement.setString(++parameterIndex, user.getPassword());
            preparedStatement.setString(++parameterIndex, String.valueOf(user.getGender()));
            preparedStatement.setString(++parameterIndex, String.valueOf(user.getRole()));
            preparedStatement.setBigDecimal(++parameterIndex, user.getBalance());
            preparedStatement.setString(++parameterIndex, user.getId());
            preparedStatement.executeUpdate();
            LOGGER.info("User updated successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot update user" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public void removeUser(String id) {
        String query = SqlFactory.getFactory()
                .deleteFrom(AttributeNameConstant.USER_ATTRIBUTE)
                .where(AttributeNameConstant.ID_ATTRIBUTE)
                .equals(id)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            LOGGER.info("User deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot delete user" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public void addUser(User user) {
        String query = SqlFactory.getFactory()
                .insertInto(AttributeNameConstant.USER_ATTRIBUTE)
                .parameters(AttributeNameConstant.ID_ATTRIBUTE, AttributeNameConstant.FIRSTNAME_ATTRIBUTE,
                        AttributeNameConstant.LASTNAME_ATTRIBUTE, AttributeNameConstant.EMAIL_ATTRIBUTE,
                        AttributeNameConstant.PASSWORD_ATTRIBUTE, AttributeNameConstant.GENDER_ATTRIBUTE,
                        AttributeNameConstant.ROLE_ATTRIBUTE, AttributeNameConstant.BALANCE_ATTRIBUTE)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int parameterIndex = NumberUtils.INTEGER_ZERO;
            preparedStatement.setString(++parameterIndex, user.getId());
            preparedStatement.setString(++parameterIndex, user.getFirstname());
            preparedStatement.setString(++parameterIndex, user.getLastname());
            preparedStatement.setString(++parameterIndex, user.getEmail());
            preparedStatement.setString(++parameterIndex, user.getPassword());
            preparedStatement.setString(++parameterIndex, String.valueOf(user.getGender()));
            preparedStatement.setString(++parameterIndex, String.valueOf(user.getRole()));
            preparedStatement.setBigDecimal(++parameterIndex, user.getBalance());
            preparedStatement.executeUpdate();
            LOGGER.info("User added successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot add user" + ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public Optional<User> getUserById(String id) {
        String query = SqlFactory.getFactory()
                .selectFrom(AttributeNameConstant.USER_ATTRIBUTE)
                .where(AttributeNameConstant.ID_ATTRIBUTE)
                .equals(id)
                .build();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            LOGGER.info("User returned successfully");
        } catch (SQLException e) {
            LOGGER.error("Cannot get user" + ExceptionUtils.getMessage(e));
        }
        return Optional.empty();
    }
}