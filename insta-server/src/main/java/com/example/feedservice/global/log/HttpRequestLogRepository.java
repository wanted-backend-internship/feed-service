package com.example.feedservice.global.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpRequestLogRepository extends JpaRepository<HttpRequestLog, Long> {
}
