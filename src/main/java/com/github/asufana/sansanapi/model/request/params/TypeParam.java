package com.github.asufana.sansanapi.model.request.params;

/**
 * 対象とするタグの種別
 */
public enum TypeParam {
    Private,
    Public,
    Shared;
    
    public String url() {
        return String.format("type=%s", name().toLowerCase());
    }
}
