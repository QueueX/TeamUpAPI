CREATE TABLE IF NOT EXISTS "Users" (
                                       "id" uuid NOT NULL UNIQUE,
                                       "name" varchar(20) NOT NULL,
                                       "lastname" varchar(20) NOT NULL,
                                       "email" varchar(255) NOT NULL UNIQUE ,
                                       "is_activated" boolean NOT NULL,
                                       "password" text NOT NULL,
                                       "role" varchar(5) NOT NULL,
                                       "refresh_token" text NOT NULL,
                                       PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Summary_Categories" (
                                                    "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
                                                    "label" varchar(50) NOT NULL UNIQUE,
                                                    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Summary_Sub_Categories" (
                                                        "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
                                                        "label" varchar(50) NOT NULL UNIQUE,
                                                        PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Status" (
                                        "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
                                        "label" varchar(20) NOT NULL UNIQUE,
                                        PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Project_Categories" (
                                                    "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
                                                    "label" varchar(50) NOT NULL UNIQUE,
                                                    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Vacancies" (
                                           "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
                                           "name" varchar(20) NOT NULL,
                                           "about" text NOT NULL,
                                           PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Skills" (
                                        "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
                                        "label" varchar(20) NOT NULL UNIQUE,
                                        PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "Verification_Codes" (
                                                    "email" varchar(255) NOT NULL UNIQUE ,
                                                    "verification_code" varchar(6) NOT NULL ,
                                                    PRIMARY KEY ("email"),
                                                    CONSTRAINT "user_email" FOREIGN KEY ("email") REFERENCES "Users"("email")
);

CREATE TABLE IF NOT EXISTS "Projects" (
                                          "id" uuid NOT NULL UNIQUE,
                                          "user" uuid NOT NULL,
                                          "name" varchar(50) NOT NULL,
                                          "photo" bytea,
                                          "category" bigint NOT NULL,
                                          "about" text NOT NULL,
                                          "vacancies" bigint NOT NULL,
                                          PRIMARY KEY ("id"),
                                          CONSTRAINT "Projects_fk1" FOREIGN KEY ("user") REFERENCES "Users"("id"),
                                          CONSTRAINT "Projects_fk4" FOREIGN KEY ("category") REFERENCES "Project_Categories"("id"),
                                          CONSTRAINT "Projects_fk6" FOREIGN KEY ("vacancies") REFERENCES "Vacancies"("id")
);

CREATE TABLE IF NOT EXISTS "Summaries" (
                                           "id" uuid NOT NULL UNIQUE,
                                           "user" uuid NOT NULL,
                                           "name" varchar(50) NOT NULL,
                                           "photo" bytea NOT NULL,
                                           "category" bigint NOT NULL,
                                           "sub_category" bigint NOT NULL,
                                           "status" bigint NOT NULL,
                                           "github" varchar(100) NOT NULL,
                                           "about" text NOT NULL,
                                           PRIMARY KEY ("id"),
                                           CONSTRAINT "Summaries_fk1" FOREIGN KEY ("user") REFERENCES "Users"("id"),
                                           CONSTRAINT "Summaries_fk4" FOREIGN KEY ("category") REFERENCES "Summary_Categories"("id"),
                                           CONSTRAINT "Summaries_fk5" FOREIGN KEY ("sub_category") REFERENCES "Summary_Sub_Categories"("id"),
                                           CONSTRAINT "Summaries_fk6" FOREIGN KEY ("status") REFERENCES "Status"("id")
);

CREATE TABLE IF NOT EXISTS "Applications" (
                                              "id" uuid NOT NULL UNIQUE,
                                              "summary" uuid NOT NULL,
                                              "project" uuid NOT NULL,
                                              "status" varchar(15) NOT NULL,
                                              PRIMARY KEY ("id"),
                                              CONSTRAINT "Applications_fk1" FOREIGN KEY ("summary") REFERENCES "Summaries"("id"),
                                              CONSTRAINT "Applications_fk2" FOREIGN KEY ("project") REFERENCES "Projects"("id")
);

CREATE TABLE IF NOT EXISTS "Vacancies_Skills" (
                                                  "vacancy_id" bigint NOT NULL,
                                                  "skill_id" bigint NOT NULL,
                                                  PRIMARY KEY ("vacancy_id", "skill_id"),
                                                  CONSTRAINT "Vacancies_Skills_fk1" FOREIGN KEY ("vacancy_id") REFERENCES "Vacancies"("id"),
                                                  CONSTRAINT "Vacancies_Skills_fk2" FOREIGN KEY ("skill_id") REFERENCES "Skills"("id")
);

CREATE TABLE IF NOT EXISTS "Summaries_Skills" (
                                                  "summary_id" uuid NOT NULL,
                                                  "skill_id" bigint NOT NULL,
                                                  PRIMARY KEY ("summary_id", "skill_id"),
                                                  CONSTRAINT "Summaries_Skills_fk1" FOREIGN KEY ("summary_id") REFERENCES "Summaries"("id"),
                                                  CONSTRAINT "Summaries_Skills_fk2" FOREIGN KEY ("skill_id") REFERENCES "Skills"("id")
);
