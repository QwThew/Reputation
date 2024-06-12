package dev.thew.reputation.utils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocalDatabase{
    String user;
    String password;
    String database;
    String port;
}
