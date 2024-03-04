package com.krd.TestTaskNotes.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String API = "/api/v1";
    public static final String DETAIL_ADDRESS = "/detail/";
    public static final String UPDATE_ADDRESS = "/update/";
    public static final String DELETE_ADDRESS = "/delete/";

    public static final String VARIABLE = "{id}";
    public static final String SEARCH_ADDRESS = "/search";

    public static final String ATTRIBUTE_BY_ALL_RESPONSE_LIST = "list_notes";
    public static final String ATTRIBUTE_BY_ID_RESPONSE = "note_by_id";
    public static final String ATTRIBUTE_FOR_UPDATE = "note_for_update";
    public static final String INDEX_PAGE = "index";
    public static final String DETAILS_PAGE = "note-details";
    public static final String ERROR_GET_BY_ID = "Заметки не существует";
    public static final String ADD_NOTE_URI = "/add";
    public static final String REDIRECT = "redirect:/api/v1/";
    public static final String ADD_PAGE = "add";
    public static final String EDIT_PAGE = "note-edit";
    public static final String ERROR_QUERY = "Неправильный запрос поиска";
    public static final String ERROR_UPDATE = "Неправильный запрос на изменение";
}
