package onl.tran.service.util;

import onl.tran.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserDetails {

 /**
  * GET CURRENT USER OBJECT
  */
 public static User getCurrentUser() {
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  Object principal = authentication.getPrincipal();
  if (authentication.isAuthenticated() && principal instanceof User) {
   return (User) principal;
  }
  return null;
 }

 /**
  * GET CURRENT USER'S PHONE NUMBER
  */
 public static String getCurrentUserPhoneNumber() {
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  Object principal = authentication.getPrincipal();
  if (authentication.isAuthenticated() && principal instanceof User) {
   return ((User) principal).getPhoneNumber();
  }
  return null;
 }

 public static Long getCurrentUserId() {
  return getCurrentUser().getId();
 }
}
