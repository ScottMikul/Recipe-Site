package com.teamtreehouse.favorite;

import org.springframework.data.repository.CrudRepository;

public interface FavoriteRepository  extends CrudRepository<Favorite,Long> {
}
