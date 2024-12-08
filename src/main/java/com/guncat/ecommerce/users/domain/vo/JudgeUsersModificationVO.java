package com.guncat.ecommerce.users.domain.vo;

import com.guncat.ecommerce.common.vo.JudgeModificationVO;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.enums.Role;
import lombok.Getter;
import lombok.ToString;
import org.junit.Assert;

@Getter
@ToString(callSuper = true)
public class JudgeUsersModificationVO extends JudgeModificationVO<UsersDTO> {

    private final boolean isRoleModified;
    private final boolean isEnabledModified;
    private final boolean isLockedModified;

    public JudgeUsersModificationVO(UsersDTO existing, UsersDTO modified) {
        super(existing, modified);
        this.isRoleModified = isRoleModified(existing, modified);
        this.isEnabledModified = isEnabledModified(existing, modified);
        this.isLockedModified = isLockedModified(existing, modified);
    }

    @Override
    protected boolean isBasicDataModified(UsersDTO existing, UsersDTO modified) {
        /*
            Basic Data in Users : Role, IsEnabled, IsLocked 를 제외한 모든 데이터.
            해당 데이터는 오로지 계정 사용자만 변경할 수 있다.
         */
        Assert.assertEquals("You must judge modifications only with same user-code data.",
                existing.getUserCode(), modified.getUserCode());

        if (!existing.getUserId().equals(modified.getUserId())) {
            return true;
        } else if (!existing.getName().equals(modified.getName())) {
            return true;
        } else if (!existing.getEmail().equals(modified.getEmail())) {
            return true;
        } else if (!existing.getTel().equals(modified.getTel())) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean isEnumStatusModified(UsersDTO existing, UsersDTO modified) {
        return isRoleModified(existing, modified) || isEnabledModified(existing, modified) || isLockedModified(existing, modified);
    }

    private boolean isRoleModified(UsersDTO existing, UsersDTO modified) {
        return !existing.getRoleList().equals(modified.getRoleList());
    }

    private boolean isEnabledModified(UsersDTO existing, UsersDTO modified) {
        return !modified.getIsEnabled().equals(existing.getIsEnabled());
    }

    private boolean isLockedModified(UsersDTO existing, UsersDTO modified) {
        return !modified.getIsLocked().equals(existing.getIsLocked());
    }
}
