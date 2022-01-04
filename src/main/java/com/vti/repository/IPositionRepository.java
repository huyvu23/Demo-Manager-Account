package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.entiy.Position;

public interface IPositionRepository extends JpaRepository<Position, Short> {

}
