package com.findinmymh.dto;

import java.util.Map;

public record APIResponse<T>(boolean success, String message, T data, Map<String, String> error) {
}
