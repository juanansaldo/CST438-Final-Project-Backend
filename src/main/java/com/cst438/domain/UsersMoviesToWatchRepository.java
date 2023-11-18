package com.cst438.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UsersMoviesToWatchRepository extends CrudRepository<UsersMoviesToWatch, Integer> {
    
	List<UsersMoviesToWatch> findByUserId(int id);
    
    void deleteById(int id);
}
