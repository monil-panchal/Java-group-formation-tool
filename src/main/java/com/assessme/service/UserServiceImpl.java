package com.assessme.service;

import com.assessme.db.dao.UserDAO;
import com.assessme.db.dao.UserDAOImpl;
import com.assessme.model.Course;
import com.assessme.model.Role;
import com.assessme.model.User;
import com.assessme.model.UserRole;
import com.assessme.model.UserRoleDTO;
import com.assessme.model.UserToken;
import com.assessme.util.AppConstant;
import com.assessme.util.BcryptPasswordEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: monil Created on: 2020-05-28
 */

/**
 * User service layer class for this application
 */
@Service
public class UserServiceImpl implements UserService {

  private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private UserDAO userDAOImpl;
  private UserTokenServiceImpl userTokenServiceImpl;
  private RoleServiceImpl roleServiceImpl;
  private UserRoleServiceImpl userRoleServiceImpl;
  private EnrollmentService enrollmentService;
  private CourseService courseService;

  public UserServiceImpl(UserDAOImpl userDAO, RoleServiceImpl roleService,
      UserRoleServiceImpl userRoleService, UserTokenServiceImpl userTokenService,
      EnrollmentService enrollmentService, CourseService courseService) {
    this.userTokenServiceImpl = userTokenService;
    this.userDAOImpl = userDAO;
    this.roleServiceImpl = roleService;
    this.userRoleServiceImpl = userRoleService;
    this.enrollmentService = enrollmentService;
    this.courseService = courseService;
  }

  /**
   * Service method for retrieving all users
   */
  public Optional<List<User>> getUserList() throws Exception {

    Optional<List<User>> userList = Optional.empty();
    try {
      userList = Optional.of(userDAOImpl.getAllUser());

      String resMessage = String.format("User list has been retrieved from the database");
      logger.info(resMessage);

    } catch (Exception e) {
      String errMessage = String.format("Error in retrieving the user list from the database");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return userList;
  }

  /**
   * Service method for retrieving user based on email
   */
  public Optional<User> getUserFromEmail(String email) throws Exception {

    Optional<User> user;
    try {
      user = userDAOImpl.getUserByEmail(email);
      String resMessage = String
          .format("User with email: %s has been retrieved from the database", email);
      logger.info(resMessage);
    } catch (Exception e) {
      String errMessage = String.format("Error in retrieving the user from the database");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return user;
  }

  /**
   * Service method for retrieving user based on email
   */
  public Optional<UserRoleDTO> getUserWithRolesFromEmail(String email) throws Exception {

    Optional<UserRoleDTO> user;
    try {
      user = userDAOImpl.getUserWithRolesFromEmail(email);
      String resMessage = String
          .format("User with email: %s has been retrieved from the database", email);
      logger.info(resMessage);
    } catch (Exception e) {
      String errMessage = String.format("Error in retrieving the user from the database");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return user;
  }


  /**
   * Service method for inserting user record
   */
  public Optional<User> addUser(User user, String userRole) throws Exception {

    logger.info("user: " + user);

    Optional<User> newUser = Optional.empty();
    Optional<Role> role = Optional.empty();
    Optional<UserRole> newUserRole = Optional.empty();
    try {

      //TODO
      //Step -1
      //Validate the User object

      if (user.getActive() == null) {
        user.setActive(true);
      }

      //Step-2
      // Check for password
      String userPassword = null;

      if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword()
          .isEmpty()) {
        // generate default Password
        userPassword = user.getBannerId() + "_" + user.getLastName();
        logger
            .info(String
                .format("User: %s default password is: %s", user.getEmail(), userPassword));
      } else {
        userPassword = user.getPassword();
      }

      //encrypt password using bcryptEncoder
      String encyptedPassword = BcryptPasswordEncoder.getbCryptPasswordFromPlainText(userPassword);
      user.setPassword(encyptedPassword);

      // Step-3 Insert user record in the user table
      newUser = userDAOImpl.addUser(user);
      if (newUser.isPresent()) {
        //  newUser = Optional.of(user);
        String resMessage = String
            .format("User with email: %s has been successfully added to the user table",
                user.getEmail());
        logger.info(resMessage);
      } else {
        throw new Exception(
            String.format("Error in creating a user with email: %s", user.getEmail()));
      }

      // Step-4 Get role_id from role table

      if (userRole == null || userRole.isBlank() || userRole.isBlank()) {
        userRole = AppConstant.DEFAULT_USER_ROLE_CREATE;
      }
      role = roleServiceImpl.getRoleFromRoleName(userRole);

      if (role.isPresent()) {

        // Step-5 Update user_role table
        newUserRole = userRoleServiceImpl
            .addUserRole(newUser.get().getUserId(), role.get().getRoleId());

      } else {
        throw new Exception("Unable to fetch role id for the user from the role table.");
      }

      if (newUser.isPresent()) {
        String resMessage = String
            .format("User: %s has been assigned with the role: %s in the system",
                user.getEmail(),
                userRole);
        logger.info(resMessage);
      } else {
        throw new Exception(String
            .format("Unable to assign the role: %s to the user: %s", userRole,
                user.getEmail()));
      }

    } catch (Exception e) {
      String errMessage = String.format("Error in adding the user to the database");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return newUser;
  }

  @Override
  public Optional<UserRoleDTO> updateUserRole(User user, String userRole) throws Exception {

    Optional<UserRoleDTO> updatedUserWithRole = Optional.empty();
    try {
      // fetching the user object from the db
      Optional<User> existingUser = getUserFromEmail(user.getEmail());

      // fetching the role object from the db
      Optional<Role> newUserRole = roleServiceImpl.getRoleFromRoleName(userRole);

      // Adding the new role to the user_role
      userRoleServiceImpl
          .addUserRole(existingUser.get().getUserId(), newUserRole.get().getRoleId());

      //Fetch the updated user object with all roles
      updatedUserWithRole = getUserWithRolesFromEmail(user.getEmail());

      String resMessage = String
          .format("User: %s has been assigned with the role: %s in the system", user.getEmail(),
              userRole);
      logger.info(resMessage);

    } catch (Exception e) {
      String errMessage = String.format("Error in updating the user role in the database");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return updatedUserWithRole;
  }

  @Override
  public Optional<User> updateUserPassword(User user, String newPassword) throws Exception {
    Optional<User> updatedUser = Optional.empty();
    try {
      // fetching the user object from the db
      Optional<User> existingUser = getUserFromEmail(user.getEmail());

      //encrypt password using bcryptEncoder
      String encyptedPassword = BcryptPasswordEncoder.getbCryptPasswordFromPlainText(newPassword);
      existingUser.get().setPassword(encyptedPassword);

      //Fetch the updated user object with all roles
      updatedUser = userDAOImpl.updateUserPassword(existingUser.get());

      String resMessage = String
          .format("User: %s password has been updated in the system", user.getEmail());
      logger.info(resMessage);

    } catch (Exception e) {
      String errMessage = String.format("Error in updating the user password in the system");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return updatedUser;
  }

  @Override
  public Optional<UserToken> addUserToken(String email) throws Exception {

    Optional<User> user = Optional.empty();
    Optional<UserToken> newUserToken = Optional.empty();

    try {
      user = getUserFromEmail(email);
      if (user.isEmpty()) {
        throw new UsernameNotFoundException("No user found");
      }
      String token = UUID.randomUUID().toString();
      UserToken userToken = new UserToken(user.get().getUserId(), token);
      newUserToken = userTokenServiceImpl.addUserToken(userToken);

      String resMessage = String.format("User: %s token has been generated in the system", email);
      logger.info(resMessage);

    } catch (Exception e) {
      String errMessage = String.format("Error in generating the user token in the system");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return newUserToken;
  }

  @Override
  public Optional<UserToken> getUserToken(String email) throws Exception {

    Optional<User> user = Optional.empty();
    Optional<UserToken> newUserToken = Optional.empty();

    try {
      user = getUserFromEmail(email);
      if (user.isEmpty()) {
        throw new UsernameNotFoundException("No user found");
      }

      newUserToken = userTokenServiceImpl.getUserToken(user.get().getUserId());

      String resMessage = String.format("User: %s token has been retrieved from  the system", email,
          newUserToken.get().getToken());
      logger.info(resMessage);

    } catch (Exception e) {
      String errMessage = String.format("Error in fetching the user token in the system");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return newUserToken;
  }

  @Override
  public Optional<List<User>> getUsersNotAssignedForCourse(String courseCode) throws Exception {

    Optional<List<User>> userList = Optional.empty();
    try {
      Optional<Course> courseWithCode = courseService.getCourseWithCode(courseCode);

      userList = Optional.of(
          userDAOImpl.getUserNotAssignedForCourse(courseWithCode.get().getCourseId())
      );
      String resMessage = String.format("User list has been retrieved from the database");
      logger.info(resMessage);
    } catch (Exception e) {
      String errMessage = String.format("Error in retrieving the user list from the database");
      logger.error(errMessage);
      e.printStackTrace();
      throw e;
    }
    return userList;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<UserRoleDTO> userRoleDTO = Optional.empty();
    try {
      userRoleDTO = getUserWithRolesFromEmail(email);

      if (userRoleDTO.isEmpty()) {
        throw new UsernameNotFoundException("Invalid username and password or user not found");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return new org.springframework.security.core.userdetails.User(userRoleDTO.get().getEmail(),
        userRoleDTO.get().getPassword(),
        mapRolesToAuthorities(userRoleDTO.get().getUserRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<String> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
  }
}