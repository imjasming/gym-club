create table oauth_client_details
(
    client_id               varchar(255) not null primary key,
    access_token_validity   int          null,
    additional_information  varchar(255) null,
    authorities             varchar(255) null,
    authorized_grant_types  varchar(255) null,
    autoapprove             varchar(255) null,
    client_secret           varchar(255) null,
    refresh_token_validity  int          null,
    resource_ids            varchar(255) null,
    scope                   varchar(255) null,
    web_server_redirect_uri varchar(255) null
);
INSERT INTO `oauth_client_details`
VALUES ('client', NULL, NULL, NULL, 'password,authorization_code,refresh_token', NULL, '{noop}secret', NULL, NULL,
        'all', NULL);

create table `userconnection`
(
    #`id`             int auto_increment,
    `userId`         varchar(255) not null,
    `providerId`     varchar(255),
    `providerUserId` varchar(255),
    `displayName`    varchar(255),
    `profileUrl`     varchar(255),
    `imageUrl`       varchar(255),
    `accessToken`    varchar(255),
    `secret`         varchar(255),
    `refreshToken`   varchar(255),
    `expireTime`     bigint,
    `rank`           int,
    primary key (userId, providerId, providerUserId)
);
create unique index UserConnectionRank on UserConnection (userId, providerId, rank);