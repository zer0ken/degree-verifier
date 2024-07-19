package org.konkuk.degreeverifier.ui;

public class Strings {
    /** CommitFrame */
    public static final String APP_TITLE = "마이크로디그리 이수 검사기";

    public static final String COMMITTING_TITLE = "%s";
    public static final String FILE_MENU = "파일";
    public static final String VERIFY_MENU = "검사";
    public static final String COMMIT_MENU = "검토";
    public static final String SETTING_MENU = "설정";

    public static final String VERIFYING_ALL = "모든 학생의 인정 가능한 학위를 검사하는 중";
    public static final String VERIFIER_LOADING_MESSAGE = "검사 기준을 불러오는 중";
    public static final String TRANSCRIPT_LOADING_MESSAGE = "성적표를 불러오는 중";
    public static final String TRANSCRIPT_SPLIT_TITLE = "성적표 파일 자동 분할";
    public static final String TRANSCRIPT_SPLIT_MESSAGE = "<html><p>성적표 파일의 크기가 너무 커 파일을 자동으로 분할했습니다.</p>" +
            "<p>분할된 성적표를 각각 검토해주세요.</p></html>";
    public static final String COMMIT_LOADING_MESSAGE = "인정한 학위를 불러오는 중";

    public static final String NO_TASK = "진행 중인 작업 없음";
    public static final String TASKS_IN_PROGRESS = "작업 진행 중... [%d개 중 %d개 완료]";
    public static final String TASKS_IN_PROGRESS_DETAILED = "작업 진행 중... [%d개 남음]";
    public static final String AUTO_COMMITTING_ALL = "자동으로 모든 학생의 학위를 인정하는 중";

    public static final String LOAD_VERIFIER_LIST = "검사 기준 불러오기";
    public static final String LOAD_VERIFIER_DIALOG_TITLE = LOAD_VERIFIER_LIST + " - 검사 기준이 위치한 폴더 선택";

    public static final String EXPORT_COMMIT = "인정한 학위 저장하기";
    public static final String EXPORT_COMMIT_DIALOG_TITLE = EXPORT_COMMIT + " - 출력 위치 선택";

    public static final String STUDENT_LIST = "학생";
    public static final String VERIFY_ALL_STUDENT = "모든 학생의 학위 다시 검사하기";
    public static final String START_COMMIT = "검토 시작하기";
    public static final String START_COMMIT_NEXT = "다음 학생 검토하기";
    public static final String START_COMMIT_PREVIOUS = "이전 학생 검토하기";

    public static final String LOAD_TRANSCRIPT = "성적표 불러오기";
    public static final String LOAD_TRANSCRIPT_DIALOG_TITLE = "성적표 파일(.csv) 선택";
    public static final String LOAD_COMMIT = "기존 학위 불러오기";
    public static final String LOAD_COMMIT_DIALOG_TITLE = "기존 학위 파일(.csv) 선택";

    public static final String VERIFIED_DEGREE_LIST = "검사한 학위";
    public static final String COMMIT_VERIFIED_DEGREE = "인정하기";
    public static final String COMMIT_RECOMMENDED_DEGREES = "권장 조합으로 인정하기";
    public static final String COMMIT_ALL_STUDENT = "모든 학생 권장 조합으로 인정하기";
    public static final String OPEN_VERIFIER_DIRECTORY = "검사 기준 위치 열기";
    public static final String INSUFFICIENT_DEGREES = "충돌이 발생하는 학위";
    public static final String SUFFICIENT_DEGREES = "인정 가능한 학위";
    public static final String NOT_VERIFIED_DEGREES = "조건을 만족하지 못한 학위";
    public static final String USED_LECTURES = "사용된 교과목(%d)";
    public static final String COMMIT_RECOMMENDED_DEGREES_DIALOG_TITLE = COMMIT_RECOMMENDED_DEGREES;
    public static final String COMMIT_RECOMMENDED_DEGREES_DIALOG_MESSAGE = "<html><p>다음 학위를 인정하려면 [확인]을 누르세요.</p>%s</html>";

    public static final String COMMITTED_DEGREE_LIST = "인정한 학위";
    public static final String DECOMMIT_DEGREE = "인정 취소하기";
    public static final String CLEAR_COMMITTED_DEGREE = "인정한 학위 초기화하기";
    public static final String EXPORT_COMMITTED_DEGREE = "인정한 학위 저장하기";
    public static final String APPEND_EXPORT_MESSAGE = "<html><p>이 출력 내용은 기존 파일의 맨 뒤에 추가됩니다.</p>" +
            "<p>계속 출력하시려면 [확인]을 누르세요.</p></html>";
    public static final String CLEAR_COMMIT_DIALOG_TITLE = CLEAR_COMMITTED_DEGREE;
    public static final String CLEAR_COMMIT_DIALOG_MESSAGE = "<html><p>다음 학위의 인정을 취소하려면 [확인]을 누르세요.</p>%s</html>";
    public static final String EARLY_COMMITTED_DEGREE = "기존 학위";
    public static final String NEWLY_COMMITTED_DEGREE = "인정한 학위";

    public static final String LECTURE_LIST = "수강 내역";

    public static final String INFORMATION = "자세히 보기";
    public static final String FOLD_INFORMATION_TREE = "한 단계 접기";
    public static final String UNFOLD_INFORMATION_TREE = "한 단계 펼치기";

    public static final String LOAD_ALIASES = "동일 교과 목록 불러오기";
    public static final String LOAD_ALIASES_DIALOG_TITLE = "동일 교과 목록 파일(.csv) 선택";


    /** EditorFrame */
    public static final String EDIT_FRAME_TITLE = "검사 기준 편집하기";
    
    public static final String APPLY_EDIT = "변경 사항 적용";
    public static final String CONFIRM_APPLY_EDIT_MESSAGE = "모든 변경 사항을 저장하려면 [확인]을 누르세요.";
    public static final String ROLLBACK_EDIT = "변경 사항 삭제";
    public static final String CONFIRM_ROLLBACK_EDIT_MESSAGE = "모든 변경 사항을 되돌리려면 [확인]을 누르세요.";
    public static final String CONFIRM_EDIT = "확인";
    public static final String CANCEL_EDIT = "취소";

    public static final String VERIFIER_TREE = "자세히 보기";

    public static final String VERIFIER_LIST = "학위 검사 기준";

    public static final String LECTURE_VERIFIER = "교과목 검사로 설정";
    public static final String CRITERIA_VERIFIER = "검사 그룹으로 설정";

    public static final String EDIT_PANEL_TITLE = "검사 기준 편집";

    public static final String OPEN_VERIFIER_FILE = "검사 기준 파일 열기";
    public static final String CREATE_DEGREE_VERIFIER = "새 학위 추가하기";
    public static final String REMOVE_DEGREE_VERIFIER = "학위 삭제하기";
    public static final String CONFIRM_REMOVE_DEGREE_MESSAGE = "선택한 학위를 삭제하려면 [확인]을 누르세요.";
    public static final String CREATE_RECURSIVE_VERIFIER = "새 검사 기준 추가하기";
    public static final String REMOVE_RECURSIVE_VERIFIER = "검사 기준 삭제하기";
    public static final String CONFIRM_REMOVE_RECURSIVE_MESSAGE = "선택한 검사 기준을 삭제하려면 [확인]을 누르세요.";
    public static final String ROLLBACK_SELECTED_NODE = "변경 되돌리기";
    public static final String CONFIRM_ROLLBACK_SELECTED_NODE_MESSAGE = "선택한 검사 기준의 변경을 모두 되돌리려면 [확인]을 누르세요.";
    public static final String CREATE_NEW_VERSION = "새 버전 만들기";

    public static final String VERIFIER_SAVING_MESSAGE = "검사 기준을 저장하는 중";
}
