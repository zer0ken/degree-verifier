package org.konkuk.degreeverifier.ui;

public class Strings {
    public static final String APP_TITLE = "이수 판정 SW";

    public static final String COMMITTING_TITLE = APP_TITLE + " / 검토중 - %s";
    public static final String FILE_MENU = "파일";
    public static final String VERIFY_MENU = "검사";

    public static final String LECTURES_LOADING_MESSAGES = "수강 내역을 불러오는 중... [%s]";
    public static final String VERIFYING = "인정 가능한 학위를 검사하는 중... [%s]";
    public static final String VERIFIER_LOADING_MESSAGE = "검사 기준을 불러오는 중";
    public static final String STUDENTS_LOADING_MESSAGE = "학생 정보를 불러오는 중";

    public static final String NO_TASK = "진행 중인 작업 없음";
    public static final String TASKS_IN_PROGRESS = "작업 진행 중... [%d개 중 %d개 완료]";
    public static final String TASKS_IN_PROGRESS_DETAILED = "작업 진행 중... [%d개 남음]";
    public static final String EXPORTING_COMMIT_MESSAGE = "인정한 학위 저장 중... [%s]";

    public static final String LOAD_VERIFIER_LIST = "검사 기준 불러오기";

    public static final String STUDENT_LIST = "학생 목록";
    public static final String VERIFY_STUDENT = "학위 검사하기";
    public static final String START_COMMIT = "학위 자세히 검토하기";
    public static final String LOAD_STUDENT_LIST = "학생 목록 불러오기";
    public static final String OPEN_STUDENT_DIRECTORY = "학생 목록 위치 열기";
    public static final String CREATE_STUDENT = "새 학생 추가";
    public static final String CREATE_STUDENT_MESSAGE = "<html>추가할 학생의 정보를 입력하고 [확인]을 누르세요.<br>&nbsp;</html>";
    public static final String STUDENT_ID = "학번*";
    public static final String STUDENT_NAME = "이름*";

    public static final String STUDENT_ID_IS_VALID = "<html>&nbsp;<br>&nbsp;</html>";
    public static final String STUDENT_ID_IS_EMPTY = "<html>학번은 비워둘 수 없습니다.<br>&nbsp;</html>";
    public static final String STUDENT_NAME_IS_EMPTY = "<html>이름은 비워둘 수 없습니다.<br>&nbsp;</html>";
    public static final String STUDENT_NAME_IS_INVALID = "<html>이름은 다음을 포함할 수 없습니다:<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "\\ / : * ? \" &lt; > | -</html>";

    public static final String VERIFIED_DEGREE_LIST = "검사한 학위 목록";
    public static final String COMMIT_VERIFIED_DEGREE = "인정하기";
    public static final String COMMIT_RECOMMENDED_DEGREES = "권장 조합으로 인정하기";
    public static final String OPEN_VERIFIER_DIRECTORY = "검사 기준 위치 열기";
    public static final String INSUFFICIENT_DEGREES = "충돌이 발생하는 학위";
    public static final String SUFFICIENT_DEGREES = "인정 가능한 학위";
    public static final String COMMIT_RECOMMENDED_DEGREES_DIALOG_TITLE = COMMIT_RECOMMENDED_DEGREES;
    public static final String COMMIT_RECOMMENDED_DEGREES_DIALOG_MESSAGE = "<html><p>다음 학위를 인정하려면 [확인]을 누르세요.</p>%s</html>";

    public static final String COMMITTED_DEGREE_LIST = "인정한 학위 목록";
    public static final String DECOMMIT_DEGREE = "인정 취소하기";
    public static final String CLEAR_COMMITTED_DEGREE = "인정한 학위 초기화하기";
    public static final String EXPORT_COMMITTED_DEGREE = "인정한 학위 저장하기";
    public static final String OPEN_EXPORT_DIRECTORY = "출력 위치 열기";
    public static final String CLEAR_COMMIT_DIALOG_TITLE = CLEAR_COMMITTED_DEGREE;
    public static final String CLEAR_COMMIT_DIALOG_MESSAGE = "<html><p>다음 학위의 인정을 취소하려면 [확인]을 누르세요.</p>%s</html>";

    public static final String LECTURE_LIST = "수강 내역";
    public static final String OPEN_LECTURE_DIRECTORY = "수강 내역 위치 열기";

    public static final String INFORMATION = "자세한 정보";
    public static final String FOLD_INFORMATION_TREE = "한 단계 접기";
    public static final String UNFOLD_INFORMATION_TREE = "한 단계 펼치기";
}
