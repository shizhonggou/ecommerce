package com.isechome.ecommerce.entity;

import java.io.Serializable;
import java.util.Date;

public class SysAdminuser implements Serializable {
    private Integer id;

    private Integer comid;

    private Integer departid;

    private String arealname;

    private String anickname;

    private String ausername;

    private String apasswd;

    private Date apasswdupdate;

    private String aquerypasswd;

    private String mobile;

    private String job;

    private String adesc;

    private String apowerroles;

    private String awhs;

    private Boolean isallwh;

    private String email;

    private Byte ismain;

    private Boolean loginiptype;

    private String loginlockip;

    private Integer cxtnum1;

    private Integer cxtnum2;

    private Integer cxtnum3;

    private Integer status;

    private Date createdate;

    private String createuser;

    private Date updatedate;

    private String updateuser;

    private Date lastdate;

    private String lastip;

    private Byte stype;

    private Boolean islimit;

    private Byte issteel;

    private Byte powerlimit;

    private String childroot;

    private Byte firstsign;

    private String mobilecheck;

    private String telephone;

    private String city;

    private String province;

    private String address;

    private String qqnum;

    private String fax;

    private String postcode;

    private Byte sex;

    private Byte yqRegistered;

    private Byte yqIssealmember;

    private Byte yqIsyqsealmember;

    private String yqTelnumber;

    private Byte yqChangeduserinfo;

    private Long rights;

    private String idnum;

    private String tradepwd;

    private String arealname2;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComid() {
        return comid;
    }

    public void setComid(Integer comid) {
        this.comid = comid;
    }

    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    public String getArealname() {
        return arealname;
    }

    public void setArealname(String arealname) {
        this.arealname = arealname == null ? null : arealname.trim();
    }

    public String getAnickname() {
        return anickname;
    }

    public String getArealname2() {
        return arealname2;
    }

    public void setArealname2(String arealname2) {
        this.arealname2 = arealname2;
    }

    public void setAnickname(String anickname) {
        this.anickname = anickname == null ? null : anickname.trim();
    }

    public String getAusername() {
        return ausername;
    }

    public void setAusername(String ausername) {
        this.ausername = ausername == null ? null : ausername.trim();
    }

    public String getApasswd() {
        return apasswd;
    }

    public void setApasswd(String apasswd) {
        this.apasswd = apasswd == null ? null : apasswd.trim();
    }

    public Date getApasswdupdate() {
        return apasswdupdate;
    }

    public void setApasswdupdate(Date apasswdupdate) {
        this.apasswdupdate = apasswdupdate;
    }

    public String getAquerypasswd() {
        return aquerypasswd;
    }

    public void setAquerypasswd(String aquerypasswd) {
        this.aquerypasswd = aquerypasswd == null ? null : aquerypasswd.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getAdesc() {
        return adesc;
    }

    public void setAdesc(String adesc) {
        this.adesc = adesc == null ? null : adesc.trim();
    }

    public String getApowerroles() {
        return apowerroles;
    }

    public void setApowerroles(String apowerroles) {
        this.apowerroles = apowerroles == null ? null : apowerroles.trim();
    }

    public String getAwhs() {
        return awhs;
    }

    public void setAwhs(String awhs) {
        this.awhs = awhs == null ? null : awhs.trim();
    }

    public Boolean getIsallwh() {
        return isallwh;
    }

    public void setIsallwh(Boolean isallwh) {
        this.isallwh = isallwh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getIsmain() {
        return ismain;
    }

    public void setIsmain(Byte ismain) {
        this.ismain = ismain;
    }

    public Boolean getLoginiptype() {
        return loginiptype;
    }

    public void setLoginiptype(Boolean loginiptype) {
        this.loginiptype = loginiptype;
    }

    public String getLoginlockip() {
        return loginlockip;
    }

    public void setLoginlockip(String loginlockip) {
        this.loginlockip = loginlockip == null ? null : loginlockip.trim();
    }

    public Integer getCxtnum1() {
        return cxtnum1;
    }

    public void setCxtnum1(Integer cxtnum1) {
        this.cxtnum1 = cxtnum1;
    }

    public Integer getCxtnum2() {
        return cxtnum2;
    }

    public void setCxtnum2(Integer cxtnum2) {
        this.cxtnum2 = cxtnum2;
    }

    public Integer getCxtnum3() {
        return cxtnum3;
    }

    public void setCxtnum3(Integer cxtnum3) {
        this.cxtnum3 = cxtnum3;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip == null ? null : lastip.trim();
    }

    public Byte getStype() {
        return stype;
    }

    public void setStype(Byte stype) {
        this.stype = stype;
    }

    public Boolean getIslimit() {
        return islimit;
    }

    public void setIslimit(Boolean islimit) {
        this.islimit = islimit;
    }

    public Byte getIssteel() {
        return issteel;
    }

    public void setIssteel(Byte issteel) {
        this.issteel = issteel;
    }

    public Byte getPowerlimit() {
        return powerlimit;
    }

    public void setPowerlimit(Byte powerlimit) {
        this.powerlimit = powerlimit;
    }

    public String getChildroot() {
        return childroot;
    }

    public void setChildroot(String childroot) {
        this.childroot = childroot == null ? null : childroot.trim();
    }

    public Byte getFirstsign() {
        return firstsign;
    }

    public void setFirstsign(Byte firstsign) {
        this.firstsign = firstsign;
    }

    public String getMobilecheck() {
        return mobilecheck;
    }

    public void setMobilecheck(String mobilecheck) {
        this.mobilecheck = mobilecheck == null ? null : mobilecheck.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getQqnum() {
        return qqnum;
    }

    public void setQqnum(String qqnum) {
        this.qqnum = qqnum == null ? null : qqnum.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getYqRegistered() {
        return yqRegistered;
    }

    public void setYqRegistered(Byte yqRegistered) {
        this.yqRegistered = yqRegistered;
    }

    public Byte getYqIssealmember() {
        return yqIssealmember;
    }

    public void setYqIssealmember(Byte yqIssealmember) {
        this.yqIssealmember = yqIssealmember;
    }

    public Byte getYqIsyqsealmember() {
        return yqIsyqsealmember;
    }

    public void setYqIsyqsealmember(Byte yqIsyqsealmember) {
        this.yqIsyqsealmember = yqIsyqsealmember;
    }

    public String getYqTelnumber() {
        return yqTelnumber;
    }

    public void setYqTelnumber(String yqTelnumber) {
        this.yqTelnumber = yqTelnumber == null ? null : yqTelnumber.trim();
    }

    public Byte getYqChangeduserinfo() {
        return yqChangeduserinfo;
    }

    public void setYqChangeduserinfo(Byte yqChangeduserinfo) {
        this.yqChangeduserinfo = yqChangeduserinfo;
    }

    public Long getRights() {
        return rights;
    }

    public void setRights(Long rights) {
        this.rights = rights;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum == null ? null : idnum.trim();
    }

    public String getTradepwd() {
        return tradepwd;
    }

    public void setTradepwd(String tradepwd) {
        this.tradepwd = tradepwd == null ? null : tradepwd.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", comid=").append(comid);
        sb.append(", departid=").append(departid);
        sb.append(", arealname=").append(arealname);
        sb.append(", anickname=").append(anickname);
        sb.append(", ausername=").append(ausername);
        sb.append(", apasswd=").append(apasswd);
        sb.append(", apasswdupdate=").append(apasswdupdate);
        sb.append(", aquerypasswd=").append(aquerypasswd);
        sb.append(", mobile=").append(mobile);
        sb.append(", job=").append(job);
        sb.append(", adesc=").append(adesc);
        sb.append(", apowerroles=").append(apowerroles);
        sb.append(", awhs=").append(awhs);
        sb.append(", isallwh=").append(isallwh);
        sb.append(", email=").append(email);
        sb.append(", ismain=").append(ismain);
        sb.append(", loginiptype=").append(loginiptype);
        sb.append(", loginlockip=").append(loginlockip);
        sb.append(", cxtnum1=").append(cxtnum1);
        sb.append(", cxtnum2=").append(cxtnum2);
        sb.append(", cxtnum3=").append(cxtnum3);
        sb.append(", status=").append(status);
        sb.append(", createdate=").append(createdate);
        sb.append(", createuser=").append(createuser);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", updateuser=").append(updateuser);
        sb.append(", lastdate=").append(lastdate);
        sb.append(", lastip=").append(lastip);
        sb.append(", stype=").append(stype);
        sb.append(", islimit=").append(islimit);
        sb.append(", issteel=").append(issteel);
        sb.append(", powerlimit=").append(powerlimit);
        sb.append(", childroot=").append(childroot);
        sb.append(", firstsign=").append(firstsign);
        sb.append(", mobilecheck=").append(mobilecheck);
        sb.append(", telephone=").append(telephone);
        sb.append(", city=").append(city);
        sb.append(", province=").append(province);
        sb.append(", address=").append(address);
        sb.append(", qqnum=").append(qqnum);
        sb.append(", fax=").append(fax);
        sb.append(", postcode=").append(postcode);
        sb.append(", sex=").append(sex);
        sb.append(", yqRegistered=").append(yqRegistered);
        sb.append(", yqIssealmember=").append(yqIssealmember);
        sb.append(", yqIsyqsealmember=").append(yqIsyqsealmember);
        sb.append(", yqTelnumber=").append(yqTelnumber);
        sb.append(", yqChangeduserinfo=").append(yqChangeduserinfo);
        sb.append(", rights=").append(rights);
        sb.append(", idnum=").append(idnum);
        sb.append(", tradepwd=").append(tradepwd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}