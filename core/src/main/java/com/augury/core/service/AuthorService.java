package com.augury.core.service;

import com.augury.model.Author;

public interface AuthorService extends Service<Author>{
	Author getAuthorByName(String author);
}