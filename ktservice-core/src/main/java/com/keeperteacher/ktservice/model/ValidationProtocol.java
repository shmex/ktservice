package com.keeperteacher.ktservice.model;

public interface ValidationProtocol {
    String ALPHANUMERIC_WITH_SPACES_REGEX = "[a-zA-Z0-9 ]{1,255}$";
    String LETTERS_WITH_SPACES_REGEX = "[a-zA-Z ]{1,255}$";
}
