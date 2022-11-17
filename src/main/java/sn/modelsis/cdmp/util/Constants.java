package sn.modelsis.cdmp.util;

public class Constants {

  private Constants() {
    throw new IllegalStateException("Constants class");
  }

  public static final String SEND_MAIL_WITH_TEMPLATE = "/api/notification/v1/messages/email/with-template";

  public static final String SEND_MAIL_WItHOUT_TEMPLATE = "/api/notification/v1/messages/email/without-template";
}