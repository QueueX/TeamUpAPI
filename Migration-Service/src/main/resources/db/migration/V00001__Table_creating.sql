CREATE TABLE IF NOT EXISTS "users" (
    "id" uuid NOT NULL UNIQUE,
    "name" varchar(20) NOT NULL,
    "lastname" varchar(20) NOT NULL,
    "email" varchar(255) NOT NULL UNIQUE,
    "is_activated" boolean NOT NULL,
    "password" text NOT NULL,
    "role" varchar(5) NOT NULL,
    "refresh_token" text NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "summary_categories" (
    "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
    "label" varchar(50) NOT NULL UNIQUE,
    "description" text NOT NULL,
    "url" varchar(20) NOT NULL UNIQUE,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "project_categories" (
    "id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
    "label" varchar(50) NOT NULL UNIQUE,
    "description" text NOT NULL,
    "url" varchar(20) NOT NULL UNIQUE,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "verification_codes" (
    "email" varchar(255) NOT NULL UNIQUE ,
    "verification_code" varchar(60) NOT NULL ,
    PRIMARY KEY ("email")
);

CREATE TABLE IF NOT EXISTS "refresh_tokens" (
    "id" uuid NOT NULL UNIQUE,
    "user" uuid NOT NULL ,
    "token" uuid NOT NULL UNIQUE,
    CONSTRAINT "refresh_tokens_fk1" FOREIGN KEY ("user") REFERENCES "users"("id")
);

CREATE TABLE IF NOT EXISTS "projects" (
    "id" uuid NOT NULL UNIQUE,
    "user" uuid NOT NULL,
    "name" varchar(50) NOT NULL,
    "photo" bytea,
    "category" bigint NOT NULL,
    "about" text NOT NULL,
    "is_active" boolean NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "Projects_fk1" FOREIGN KEY ("user") REFERENCES "users"("id"),
    CONSTRAINT "Projects_fk4" FOREIGN KEY ("category") REFERENCES "project_categories"("id")
);

CREATE TABLE IF NOT EXISTS "summaries" (
    "id" uuid NOT NULL UNIQUE,
    "user" uuid NOT NULL,
    "name" varchar(50) NOT NULL,
    "photo" bytea,
    "category" bigint NOT NULL,
    "status" varchar(20) NOT NULL,
    "github" varchar(255),
    "about" text NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "Summaries_fk1" FOREIGN KEY ("user") REFERENCES "users"("id"),
    CONSTRAINT "Summaries_fk4" FOREIGN KEY ("category") REFERENCES "summary_categories"("id")
);

CREATE TABLE IF NOT EXISTS "applications" (
    "id" uuid NOT NULL UNIQUE,
    "summary" uuid NOT NULL,
    "project" uuid NOT NULL,
    "status" varchar(20) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "Applications_fk1" FOREIGN KEY ("summary") REFERENCES "summaries"("id"),
    CONSTRAINT "Applications_fk2" FOREIGN KEY ("project") REFERENCES "projects"("id")
);