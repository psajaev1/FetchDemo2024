package com.example.FetchDemo2024.model;

import org.springframework.lang.NonNull;

public record Item (@NonNull String shortDescription, @NonNull String price) {}
