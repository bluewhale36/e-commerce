package com.guncat.ecommerce.users.domain.vo;

/**
 * 전화번호 유효성 검증하는 VO Class.
 */
public record TelVO(String tel) {

    public TelVO(String tel) {
        if (isTelValid(telHyphen(tel))) {
            this.tel = tel;
        } else {
            throw new IllegalArgumentException("잘못된 전화번호 형식입니다.");
        }
    }

    /**
     * 전화번호 문자열에 하이픈 ("-") 추가.
     *
     * @param tel 전화번호 문자열.
     * @return 하이픈(" - ") 이 추가된 전화번호 문자열.
     */
    private String telHyphen(String tel) {
        return tel.replaceAll("[^0-9]", "")
                .replaceAll("^(\\d{3})(\\d{0,4})(\\d{0,4})$", "$1-$2-$3")
                .replaceAll("(\\-{1,2})$", "");
    }

    private boolean isTelValid(String tel) {
        String TEL_PATTERN = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        return !tel.isEmpty() && tel.matches(TEL_PATTERN);
    }

}
