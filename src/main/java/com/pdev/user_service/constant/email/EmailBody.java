package com.pdev.user_service.constant.email;

public class EmailBody {
    public static final String USER_REGISTERED_BODY = """
            <html>
              <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <h2 style="color:#2E86C1;">Welcome to REMPMS!</h2>
                <p>Hi <b>%s</b>,</p>
                <p>We're excited to let you know that your <b>REMPMS</b> account has been successfully created.</p>
                <p>You can now sign in to your account using your registered email address or username and start exploring our services.</p>
                <p style="margin-top:20px;">If you didn’t create this account, please contact our support team immediately.</p>
                <br>
                <p>Best regards,<br><b>The REMPMS Team</b></p>
                <hr>
                <small style="color:gray;">This is an automated message — please do not reply to this email.</small>
              </body>
            </html>
            """;

}
