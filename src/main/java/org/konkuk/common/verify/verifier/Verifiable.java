package org.konkuk.common.verify.verifier;

import org.konkuk.common.student.Lecture;

import java.util.List;

/**
 * 이 인터페이스는 검증에 필요한 메소드의 구현을 강제합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:43:17.262Z
 */
public interface Verifiable {
    List<LectureVerifier> match(List<Lecture> lectures);

    boolean verify();
}
