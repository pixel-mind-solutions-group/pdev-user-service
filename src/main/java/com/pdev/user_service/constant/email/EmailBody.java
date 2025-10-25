package com.pdev.user_service.constant.email;

public class EmailBody {
    public static final String USER_REGISTERED_BODY = """
            <html>
              <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f8f9fa; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1);">
            
                  <h2 style="color:#2E86C1; text-align:center;">Welcome to PIXEL HIRE!</h2>
                  <p>Hi <b>%s</b>,</p>
                  <p>We're excited to let you know that your <b>PIXEL HIRE</b> account has been successfully created.</p>
            
                  <p>Before you start exploring, please verify your email address by clicking the button below:</p>
            
                  <div style="text-align:center; margin: 30px 0;">
                    <a href="%s" 
                       style="background-color: #2E86C1; color: white; padding: 12px 25px; text-decoration: none; border-radius: 6px; font-weight: bold; display: inline-block;">
                       Verify My Email
                    </a>
                  </div>
            
                  <p>If the button above doesn't work, copy and paste the following link into your browser:</p>
                  <p style="word-break: break-all; color: #2E86C1;">%s</p>
            
                  <p style="margin-top:20px;">If you didn’t create this account, please contact our support team immediately.</p>
            
                  <br>
                  <p>Best regards,<br><b>The PIXEL HIRE Team</b></p>
                  <hr>
                  <small style="color:gray;">This is an automated message — please do not reply to this email.</small>
                </div>
              </body>
            </html>
            """;
}
