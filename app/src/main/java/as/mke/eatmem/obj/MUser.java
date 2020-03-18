package as.mke.eatmem.obj;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class MUser extends BmobUser {
    private String uniqueID;//唯一识别码

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public boolean isUnlock() {
        return isUnlock;
    }

    public void setUnlock(boolean unlock) {
        isUnlock = unlock;
    }

    public String getAliPayNumber() {
        return aliPayNumber;
    }

    public void setAliPayNumber(String aliPayNumber) {
        this.aliPayNumber = aliPayNumber;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    private boolean isUnlock;
    private String aliPayNumber;//支付宝订单号
    private String payNumber;//支付金额

}
