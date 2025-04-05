package ru.yandex.practicum.shoppingstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PageableDto {

    @NotNull(message = "page number must be not empty")
    @Min(value = 0, message = "page number must greater or equal 0")
    private int page;

    @NotNull(message = "page size must be not empty")
    @Min(value = 1, message = "page size must greater 0")
    private int size;

    private List<String> sort;

    public PageableDto(@NotNull(message = "page number must be not empty") @Min(value = 0, message = "page number must greater or equal 0") int page, @NotNull(message = "page size must be not empty") @Min(value = 1, message = "page size must greater 0") int size, List<String> sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public PageableDto() {
    }

    public static PageableDtoBuilder builder() {
        return new PageableDtoBuilder();
    }

    public @NotNull(message = "page number must be not empty") @Min(value = 0, message = "page number must greater or equal 0") int getPage() {
        return this.page;
    }

    public @NotNull(message = "page size must be not empty") @Min(value = 1, message = "page size must greater 0") int getSize() {
        return this.size;
    }

    public List<String> getSort() {
        return this.sort;
    }

    public void setPage(@NotNull(message = "page number must be not empty") @Min(value = 0, message = "page number must greater or equal 0") int page) {
        this.page = page;
    }

    public void setSize(@NotNull(message = "page size must be not empty") @Min(value = 1, message = "page size must greater 0") int size) {
        this.size = size;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PageableDto)) return false;
        final PageableDto other = (PageableDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getPage() != other.getPage()) return false;
        if (this.getSize() != other.getSize()) return false;
        final Object this$sort = this.getSort();
        final Object other$sort = other.getSort();
        if (this$sort == null ? other$sort != null : !this$sort.equals(other$sort)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageableDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPage();
        result = result * PRIME + this.getSize();
        final Object $sort = this.getSort();
        result = result * PRIME + ($sort == null ? 43 : $sort.hashCode());
        return result;
    }

    public String toString() {
        return "PageableDto(page=" + this.getPage() + ", size=" + this.getSize() + ", sort=" + this.getSort() + ")";
    }

    public static class PageableDtoBuilder {
        private @NotNull(message = "page number must be not empty")
        @Min(value = 0, message = "page number must greater or equal 0") int page;
        private @NotNull(message = "page size must be not empty")
        @Min(value = 1, message = "page size must greater 0") int size;
        private List<String> sort;

        PageableDtoBuilder() {
        }

        public PageableDtoBuilder page(@NotNull(message = "page number must be not empty") @Min(value = 0, message = "page number must greater or equal 0") int page) {
            this.page = page;
            return this;
        }

        public PageableDtoBuilder size(@NotNull(message = "page size must be not empty") @Min(value = 1, message = "page size must greater 0") int size) {
            this.size = size;
            return this;
        }

        public PageableDtoBuilder sort(List<String> sort) {
            this.sort = sort;
            return this;
        }

        public PageableDto build() {
            return new PageableDto(this.page, this.size, this.sort);
        }

        public String toString() {
            return "PageableDto.PageableDtoBuilder(page=" + this.page + ", size=" + this.size + ", sort=" + this.sort + ")";
        }
    }
}