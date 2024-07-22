package com.uwu.authenticationservice.service

import org.springframework.stereotype.Service

@Service
class MailGenerator {
    companion object {
        fun verificationMailTextGenerate(verificationCode: String) =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"ru\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Подтверждение почты</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #f7f4fc;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            color: #333;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 0 auto;\n" +
                    "            background-color: #fff;\n" +
                    "            border-radius: 10px;\n" +
                    "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                    "            overflow: hidden;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #e0d7f3;\n" +
                    "            padding: 20px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            padding: 20px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content h1 {\n" +
                    "            color: #6a4b8a;\n" +
                    "        }\n" +
                    "        .content p {\n" +
                    "            margin: 20px 0;\n" +
                    "            color: #6a4b8a;\n" +
                    "        }\n" +
                    "        .button {\n" +
                    "            display: inline-block;\n" +
                    "            padding: 15px 25px;\n" +
                    "            font-size: 16px;\n" +
                    "            color: #fff;\n" +
                    "            background-color: #a18cd1;\n" +
                    "            border-radius: 5px;\n" +
                    "            text-decoration: none;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            background-color: #e0d7f3;\n" +
                    "            padding: 10px;\n" +
                    "            text-align: center;\n" +
                    "            font-size: 12px;\n" +
                    "            color: #6a4b8a;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <img src=\"https://i.postimg.cc/63fKThwW/image.png\" alt=\"Логотип\">\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <h1>Подтверждение почты</h1>\n" +
                    "            <p>Нажмите на кнопку чтобы подтвердить свою учетную запись</p>\n" +
                    "            <a href=\"https://my-teamup.ru/confirm/mail?token=${verificationCode}\" class=\"button\">Подтвердить</a>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            &copy; 2024 TeamUp by UwU. Все права защищены.\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n"
    }

}