package org.example.university.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityServices<R,P> {
    public P save(R request);
    public P update(Long id,R request);
    public void delete(Long id);
    public P findById(Long id);
    public Page<P> findAll(Pageable pageable);
}
