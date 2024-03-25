package com.craftWine.shop.dto.data;

import java.io.Serializable;
import java.util.List;

public record DataDTO<T>(
        List<T> data,
        long totalPages,
        long totalCount) implements Serializable {
}
