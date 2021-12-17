package com.isechome.ecommerce.security.entity;
import java.io.Serializable;
import java.util.Date;

public class SysCompany implements Serializable {
    private Integer id;

    private String guid;

    private Integer memberid;

    private Integer groupid;

    private String mgId;

    private String comname;

    private String comnameshort;

    private Byte membertype;

    private String mchNo;

    private String sitNo;

    private Byte comtype;

    private Byte comtype2;

    private Byte comtype3;

    private Byte comtranstype;

    private String tradepasswd;

    private Date tradepasswdupdate;

    private Byte pysametd;

    private String mobilechecked;

    private String linkman;

    private Date mobilecheckeddate;

    private String contacttel;

    private String addressstate;

    private String addresscity;

    private String address;

    private Double mapx;

    private Double mapy;

    private String frdb;

    private Integer parentid;

    private Byte isnofinance;

    private Integer adminuser1;

    private Date adminuser1date;

    private Integer adminuser2;

    private Date adminuser2date;

    private Integer adminuser3;

    private Date adminuser3date;

    private Date lastservicedate;

    private Integer lastserviceid;

    private String contactfax;

    private String banktype;

    private String taxno;

    private String regip;

    private Date firstsigndate;

    private String country;

    private String state;

    private String city;

    private String postcode;

    private Byte registerfrom;

    private Byte status;

    private Byte zxflag;

    private String receivesmstype;

    private Date createdate;

    private String createuser;

    private Date updatedate;

    private String updateuser;

    private Byte isshowweb;

    private Date startdate;

    private Date enddate;

    private Byte empowertype;

    private Byte isshowcj;

    private Boolean isneedzs;

    private String bankno;

    private String invoicestar;

    private String invoiceno;

    private String paypasswd;

    private Byte isdowntd;

    private Date gctrybegintime;

    private Date gctryendtime;

    private Byte isgctry;

    private String comurl;

    private String bankaccount;

    private Date membertypedate;

    private Byte istry;

    private Byte issteelhq;

    private String doarea;

    private String icnumber;

    private String enterprisecode;

    private String variety1;

    private String variety2;

    private String variety3;

    private String variety4;

    private String variety5;

    private String scSteelcom;

    private String dlCom;

    private String rzMarket;

    private String jyCitys;

    private String jyVariety;

    private String zyVariety;

    private Byte isdzgys;

    private String tieluz;

    private String tieluz2;

    private String tieluz3;

    private String tieluz4;

    private String tieluz5;

    private String matou;

    private String matou2;

    private String matou3;

    private String matou4;

    private String matou5;

    private Byte istj;

    private Integer px;

    private String website2;

    private String email;

    private String areacode;

    private Byte scale;

    private String sales;

    private String turnover;

    private Byte source;

    private Integer gzjadmin1;

    private Integer gzjadmin2;

    private String kpcom;

    private String kpren;

    private String kpaddress;

    private String kptel;

    private Byte tryOut;

    private String yqCompanyname;

    private String signcompanyname;

    private Byte yqMuban;

    private Byte yqLiucheng;

    private String yjSalestype;

    private Byte allowmultifh;

    private Integer jiaogefinishnum;

    private Double pingjiazongfen1;

    private Integer pingjianum1;

    private Double pingjiazongfen2;

    private Integer pingjianum2;

    private Byte allowsendjingjiasms;

    private Byte allowdaizhucemember;

    private Integer mgAtt;

    private Double downprice;

    private Date mgEndtime;

    private Date mgStarttime;

    private Byte paytype;

    private Integer cityId;

    private Integer mgUserid;

    private String mgUsername;

    private Integer mgMid;

    private Date mgDate;

    private Byte mgManagedaozhancode;

    private String mgDaozhancode;

    private String mgDaozhancangku;

    private Byte mgTihuomode;

    private Byte mgCankaoremarkprice;

    private Byte mgCanbuyother;

    private Byte mgCanbuymaterialtype;

    private String mgMateriallist;

    private Byte mgIsxieyi;

    private Double mgXieyinum;

    private Double mgXieyinum1200;

    private Byte mgIsfengongsi;

    private Double mgLockprice;

    private Byte mgCanuseshengyukeyres;

    private Double mgMonthnum;

    private Double mgHetongtotalnum;

    private String mgCapitalpolicy;

    private Byte mgSubordinatedepartments;

    private Byte mgGcdaypricetype;

    private Double mgJijiaLuowen;

    private Double mgJijiaGaoxian;

    private Double mgJijiaPanluo;

    private String comdesc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getMgId() {
        return mgId;
    }

    public void setMgId(String mgId) {
        this.mgId = mgId == null ? null : mgId.trim();
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname == null ? null : comname.trim();
    }

    public String getComnameshort() {
        return comnameshort;
    }

    public void setComnameshort(String comnameshort) {
        this.comnameshort = comnameshort == null ? null : comnameshort.trim();
    }

    public Byte getMembertype() {
        return membertype;
    }

    public void setMembertype(Byte membertype) {
        this.membertype = membertype;
    }

    public String getMchNo() {
        return mchNo;
    }

    public void setMchNo(String mchNo) {
        this.mchNo = mchNo == null ? null : mchNo.trim();
    }

    public String getSitNo() {
        return sitNo;
    }

    public void setSitNo(String sitNo) {
        this.sitNo = sitNo == null ? null : sitNo.trim();
    }

    public Byte getComtype() {
        return comtype;
    }

    public void setComtype(Byte comtype) {
        this.comtype = comtype;
    }

    public Byte getComtype2() {
        return comtype2;
    }

    public void setComtype2(Byte comtype2) {
        this.comtype2 = comtype2;
    }

    public Byte getComtype3() {
        return comtype3;
    }

    public void setComtype3(Byte comtype3) {
        this.comtype3 = comtype3;
    }

    public Byte getComtranstype() {
        return comtranstype;
    }

    public void setComtranstype(Byte comtranstype) {
        this.comtranstype = comtranstype;
    }

    public String getTradepasswd() {
        return tradepasswd;
    }

    public void setTradepasswd(String tradepasswd) {
        this.tradepasswd = tradepasswd == null ? null : tradepasswd.trim();
    }

    public Date getTradepasswdupdate() {
        return tradepasswdupdate;
    }

    public void setTradepasswdupdate(Date tradepasswdupdate) {
        this.tradepasswdupdate = tradepasswdupdate;
    }

    public Byte getPysametd() {
        return pysametd;
    }

    public void setPysametd(Byte pysametd) {
        this.pysametd = pysametd;
    }

    public String getMobilechecked() {
        return mobilechecked;
    }

    public void setMobilechecked(String mobilechecked) {
        this.mobilechecked = mobilechecked == null ? null : mobilechecked.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public Date getMobilecheckeddate() {
        return mobilecheckeddate;
    }

    public void setMobilecheckeddate(Date mobilecheckeddate) {
        this.mobilecheckeddate = mobilecheckeddate;
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel == null ? null : contacttel.trim();
    }

    public String getAddressstate() {
        return addressstate;
    }

    public void setAddressstate(String addressstate) {
        this.addressstate = addressstate == null ? null : addressstate.trim();
    }

    public String getAddresscity() {
        return addresscity;
    }

    public void setAddresscity(String addresscity) {
        this.addresscity = addresscity == null ? null : addresscity.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Double getMapx() {
        return mapx;
    }

    public void setMapx(Double mapx) {
        this.mapx = mapx;
    }

    public Double getMapy() {
        return mapy;
    }

    public void setMapy(Double mapy) {
        this.mapy = mapy;
    }

    public String getFrdb() {
        return frdb;
    }

    public void setFrdb(String frdb) {
        this.frdb = frdb == null ? null : frdb.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Byte getIsnofinance() {
        return isnofinance;
    }

    public void setIsnofinance(Byte isnofinance) {
        this.isnofinance = isnofinance;
    }

    public Integer getAdminuser1() {
        return adminuser1;
    }

    public void setAdminuser1(Integer adminuser1) {
        this.adminuser1 = adminuser1;
    }

    public Date getAdminuser1date() {
        return adminuser1date;
    }

    public void setAdminuser1date(Date adminuser1date) {
        this.adminuser1date = adminuser1date;
    }

    public Integer getAdminuser2() {
        return adminuser2;
    }

    public void setAdminuser2(Integer adminuser2) {
        this.adminuser2 = adminuser2;
    }

    public Date getAdminuser2date() {
        return adminuser2date;
    }

    public void setAdminuser2date(Date adminuser2date) {
        this.adminuser2date = adminuser2date;
    }

    public Integer getAdminuser3() {
        return adminuser3;
    }

    public void setAdminuser3(Integer adminuser3) {
        this.adminuser3 = adminuser3;
    }

    public Date getAdminuser3date() {
        return adminuser3date;
    }

    public void setAdminuser3date(Date adminuser3date) {
        this.adminuser3date = adminuser3date;
    }

    public Date getLastservicedate() {
        return lastservicedate;
    }

    public void setLastservicedate(Date lastservicedate) {
        this.lastservicedate = lastservicedate;
    }

    public Integer getLastserviceid() {
        return lastserviceid;
    }

    public void setLastserviceid(Integer lastserviceid) {
        this.lastserviceid = lastserviceid;
    }

    public String getContactfax() {
        return contactfax;
    }

    public void setContactfax(String contactfax) {
        this.contactfax = contactfax == null ? null : contactfax.trim();
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype == null ? null : banktype.trim();
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno == null ? null : taxno.trim();
    }

    public String getRegip() {
        return regip;
    }

    public void setRegip(String regip) {
        this.regip = regip == null ? null : regip.trim();
    }

    public Date getFirstsigndate() {
        return firstsigndate;
    }

    public void setFirstsigndate(Date firstsigndate) {
        this.firstsigndate = firstsigndate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Byte getRegisterfrom() {
        return registerfrom;
    }

    public void setRegisterfrom(Byte registerfrom) {
        this.registerfrom = registerfrom;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getZxflag() {
        return zxflag;
    }

    public void setZxflag(Byte zxflag) {
        this.zxflag = zxflag;
    }

    public String getReceivesmstype() {
        return receivesmstype;
    }

    public void setReceivesmstype(String receivesmstype) {
        this.receivesmstype = receivesmstype == null ? null : receivesmstype.trim();
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

    public Byte getIsshowweb() {
        return isshowweb;
    }

    public void setIsshowweb(Byte isshowweb) {
        this.isshowweb = isshowweb;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Byte getEmpowertype() {
        return empowertype;
    }

    public void setEmpowertype(Byte empowertype) {
        this.empowertype = empowertype;
    }

    public Byte getIsshowcj() {
        return isshowcj;
    }

    public void setIsshowcj(Byte isshowcj) {
        this.isshowcj = isshowcj;
    }

    public Boolean getIsneedzs() {
        return isneedzs;
    }

    public void setIsneedzs(Boolean isneedzs) {
        this.isneedzs = isneedzs;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno == null ? null : bankno.trim();
    }

    public String getInvoicestar() {
        return invoicestar;
    }

    public void setInvoicestar(String invoicestar) {
        this.invoicestar = invoicestar == null ? null : invoicestar.trim();
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getPaypasswd() {
        return paypasswd;
    }

    public void setPaypasswd(String paypasswd) {
        this.paypasswd = paypasswd == null ? null : paypasswd.trim();
    }

    public Byte getIsdowntd() {
        return isdowntd;
    }

    public void setIsdowntd(Byte isdowntd) {
        this.isdowntd = isdowntd;
    }

    public Date getGctrybegintime() {
        return gctrybegintime;
    }

    public void setGctrybegintime(Date gctrybegintime) {
        this.gctrybegintime = gctrybegintime;
    }

    public Date getGctryendtime() {
        return gctryendtime;
    }

    public void setGctryendtime(Date gctryendtime) {
        this.gctryendtime = gctryendtime;
    }

    public Byte getIsgctry() {
        return isgctry;
    }

    public void setIsgctry(Byte isgctry) {
        this.isgctry = isgctry;
    }

    public String getComurl() {
        return comurl;
    }

    public void setComurl(String comurl) {
        this.comurl = comurl == null ? null : comurl.trim();
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount == null ? null : bankaccount.trim();
    }

    public Date getMembertypedate() {
        return membertypedate;
    }

    public void setMembertypedate(Date membertypedate) {
        this.membertypedate = membertypedate;
    }

    public Byte getIstry() {
        return istry;
    }

    public void setIstry(Byte istry) {
        this.istry = istry;
    }

    public Byte getIssteelhq() {
        return issteelhq;
    }

    public void setIssteelhq(Byte issteelhq) {
        this.issteelhq = issteelhq;
    }

    public String getDoarea() {
        return doarea;
    }

    public void setDoarea(String doarea) {
        this.doarea = doarea == null ? null : doarea.trim();
    }

    public String getIcnumber() {
        return icnumber;
    }

    public void setIcnumber(String icnumber) {
        this.icnumber = icnumber == null ? null : icnumber.trim();
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode == null ? null : enterprisecode.trim();
    }

    public String getVariety1() {
        return variety1;
    }

    public void setVariety1(String variety1) {
        this.variety1 = variety1 == null ? null : variety1.trim();
    }

    public String getVariety2() {
        return variety2;
    }

    public void setVariety2(String variety2) {
        this.variety2 = variety2 == null ? null : variety2.trim();
    }

    public String getVariety3() {
        return variety3;
    }

    public void setVariety3(String variety3) {
        this.variety3 = variety3 == null ? null : variety3.trim();
    }

    public String getVariety4() {
        return variety4;
    }

    public void setVariety4(String variety4) {
        this.variety4 = variety4 == null ? null : variety4.trim();
    }

    public String getVariety5() {
        return variety5;
    }

    public void setVariety5(String variety5) {
        this.variety5 = variety5 == null ? null : variety5.trim();
    }

    public String getScSteelcom() {
        return scSteelcom;
    }

    public void setScSteelcom(String scSteelcom) {
        this.scSteelcom = scSteelcom == null ? null : scSteelcom.trim();
    }

    public String getDlCom() {
        return dlCom;
    }

    public void setDlCom(String dlCom) {
        this.dlCom = dlCom == null ? null : dlCom.trim();
    }

    public String getRzMarket() {
        return rzMarket;
    }

    public void setRzMarket(String rzMarket) {
        this.rzMarket = rzMarket == null ? null : rzMarket.trim();
    }

    public String getJyCitys() {
        return jyCitys;
    }

    public void setJyCitys(String jyCitys) {
        this.jyCitys = jyCitys == null ? null : jyCitys.trim();
    }

    public String getJyVariety() {
        return jyVariety;
    }

    public void setJyVariety(String jyVariety) {
        this.jyVariety = jyVariety == null ? null : jyVariety.trim();
    }

    public String getZyVariety() {
        return zyVariety;
    }

    public void setZyVariety(String zyVariety) {
        this.zyVariety = zyVariety == null ? null : zyVariety.trim();
    }

    public Byte getIsdzgys() {
        return isdzgys;
    }

    public void setIsdzgys(Byte isdzgys) {
        this.isdzgys = isdzgys;
    }

    public String getTieluz() {
        return tieluz;
    }

    public void setTieluz(String tieluz) {
        this.tieluz = tieluz == null ? null : tieluz.trim();
    }

    public String getTieluz2() {
        return tieluz2;
    }

    public void setTieluz2(String tieluz2) {
        this.tieluz2 = tieluz2 == null ? null : tieluz2.trim();
    }

    public String getTieluz3() {
        return tieluz3;
    }

    public void setTieluz3(String tieluz3) {
        this.tieluz3 = tieluz3 == null ? null : tieluz3.trim();
    }

    public String getTieluz4() {
        return tieluz4;
    }

    public void setTieluz4(String tieluz4) {
        this.tieluz4 = tieluz4 == null ? null : tieluz4.trim();
    }

    public String getTieluz5() {
        return tieluz5;
    }

    public void setTieluz5(String tieluz5) {
        this.tieluz5 = tieluz5 == null ? null : tieluz5.trim();
    }

    public String getMatou() {
        return matou;
    }

    public void setMatou(String matou) {
        this.matou = matou == null ? null : matou.trim();
    }

    public String getMatou2() {
        return matou2;
    }

    public void setMatou2(String matou2) {
        this.matou2 = matou2 == null ? null : matou2.trim();
    }

    public String getMatou3() {
        return matou3;
    }

    public void setMatou3(String matou3) {
        this.matou3 = matou3 == null ? null : matou3.trim();
    }

    public String getMatou4() {
        return matou4;
    }

    public void setMatou4(String matou4) {
        this.matou4 = matou4 == null ? null : matou4.trim();
    }

    public String getMatou5() {
        return matou5;
    }

    public void setMatou5(String matou5) {
        this.matou5 = matou5 == null ? null : matou5.trim();
    }

    public Byte getIstj() {
        return istj;
    }

    public void setIstj(Byte istj) {
        this.istj = istj;
    }

    public Integer getPx() {
        return px;
    }

    public void setPx(Integer px) {
        this.px = px;
    }

    public String getWebsite2() {
        return website2;
    }

    public void setWebsite2(String website2) {
        this.website2 = website2 == null ? null : website2.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public Byte getScale() {
        return scale;
    }

    public void setScale(Byte scale) {
        this.scale = scale;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales == null ? null : sales.trim();
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover == null ? null : turnover.trim();
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Integer getGzjadmin1() {
        return gzjadmin1;
    }

    public void setGzjadmin1(Integer gzjadmin1) {
        this.gzjadmin1 = gzjadmin1;
    }

    public Integer getGzjadmin2() {
        return gzjadmin2;
    }

    public void setGzjadmin2(Integer gzjadmin2) {
        this.gzjadmin2 = gzjadmin2;
    }

    public String getKpcom() {
        return kpcom;
    }

    public void setKpcom(String kpcom) {
        this.kpcom = kpcom == null ? null : kpcom.trim();
    }

    public String getKpren() {
        return kpren;
    }

    public void setKpren(String kpren) {
        this.kpren = kpren == null ? null : kpren.trim();
    }

    public String getKpaddress() {
        return kpaddress;
    }

    public void setKpaddress(String kpaddress) {
        this.kpaddress = kpaddress == null ? null : kpaddress.trim();
    }

    public String getKptel() {
        return kptel;
    }

    public void setKptel(String kptel) {
        this.kptel = kptel == null ? null : kptel.trim();
    }

    public Byte getTryOut() {
        return tryOut;
    }

    public void setTryOut(Byte tryOut) {
        this.tryOut = tryOut;
    }

    public String getYqCompanyname() {
        return yqCompanyname;
    }

    public void setYqCompanyname(String yqCompanyname) {
        this.yqCompanyname = yqCompanyname == null ? null : yqCompanyname.trim();
    }

    public String getSigncompanyname() {
        return signcompanyname;
    }

    public void setSigncompanyname(String signcompanyname) {
        this.signcompanyname = signcompanyname == null ? null : signcompanyname.trim();
    }

    public Byte getYqMuban() {
        return yqMuban;
    }

    public void setYqMuban(Byte yqMuban) {
        this.yqMuban = yqMuban;
    }

    public Byte getYqLiucheng() {
        return yqLiucheng;
    }

    public void setYqLiucheng(Byte yqLiucheng) {
        this.yqLiucheng = yqLiucheng;
    }

    public String getYjSalestype() {
        return yjSalestype;
    }

    public void setYjSalestype(String yjSalestype) {
        this.yjSalestype = yjSalestype == null ? null : yjSalestype.trim();
    }

    public Byte getAllowmultifh() {
        return allowmultifh;
    }

    public void setAllowmultifh(Byte allowmultifh) {
        this.allowmultifh = allowmultifh;
    }

    public Integer getJiaogefinishnum() {
        return jiaogefinishnum;
    }

    public void setJiaogefinishnum(Integer jiaogefinishnum) {
        this.jiaogefinishnum = jiaogefinishnum;
    }

    public Double getPingjiazongfen1() {
        return pingjiazongfen1;
    }

    public void setPingjiazongfen1(Double pingjiazongfen1) {
        this.pingjiazongfen1 = pingjiazongfen1;
    }

    public Integer getPingjianum1() {
        return pingjianum1;
    }

    public void setPingjianum1(Integer pingjianum1) {
        this.pingjianum1 = pingjianum1;
    }

    public Double getPingjiazongfen2() {
        return pingjiazongfen2;
    }

    public void setPingjiazongfen2(Double pingjiazongfen2) {
        this.pingjiazongfen2 = pingjiazongfen2;
    }

    public Integer getPingjianum2() {
        return pingjianum2;
    }

    public void setPingjianum2(Integer pingjianum2) {
        this.pingjianum2 = pingjianum2;
    }

    public Byte getAllowsendjingjiasms() {
        return allowsendjingjiasms;
    }

    public void setAllowsendjingjiasms(Byte allowsendjingjiasms) {
        this.allowsendjingjiasms = allowsendjingjiasms;
    }

    public Byte getAllowdaizhucemember() {
        return allowdaizhucemember;
    }

    public void setAllowdaizhucemember(Byte allowdaizhucemember) {
        this.allowdaizhucemember = allowdaizhucemember;
    }

    public Integer getMgAtt() {
        return mgAtt;
    }

    public void setMgAtt(Integer mgAtt) {
        this.mgAtt = mgAtt;
    }

    public Double getDownprice() {
        return downprice;
    }

    public void setDownprice(Double downprice) {
        this.downprice = downprice;
    }

    public Date getMgEndtime() {
        return mgEndtime;
    }

    public void setMgEndtime(Date mgEndtime) {
        this.mgEndtime = mgEndtime;
    }

    public Date getMgStarttime() {
        return mgStarttime;
    }

    public void setMgStarttime(Date mgStarttime) {
        this.mgStarttime = mgStarttime;
    }

    public Byte getPaytype() {
        return paytype;
    }

    public void setPaytype(Byte paytype) {
        this.paytype = paytype;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getMgUserid() {
        return mgUserid;
    }

    public void setMgUserid(Integer mgUserid) {
        this.mgUserid = mgUserid;
    }

    public String getMgUsername() {
        return mgUsername;
    }

    public void setMgUsername(String mgUsername) {
        this.mgUsername = mgUsername == null ? null : mgUsername.trim();
    }

    public Integer getMgMid() {
        return mgMid;
    }

    public void setMgMid(Integer mgMid) {
        this.mgMid = mgMid;
    }

    public Date getMgDate() {
        return mgDate;
    }

    public void setMgDate(Date mgDate) {
        this.mgDate = mgDate;
    }

    public Byte getMgManagedaozhancode() {
        return mgManagedaozhancode;
    }

    public void setMgManagedaozhancode(Byte mgManagedaozhancode) {
        this.mgManagedaozhancode = mgManagedaozhancode;
    }

    public String getMgDaozhancode() {
        return mgDaozhancode;
    }

    public void setMgDaozhancode(String mgDaozhancode) {
        this.mgDaozhancode = mgDaozhancode == null ? null : mgDaozhancode.trim();
    }

    public String getMgDaozhancangku() {
        return mgDaozhancangku;
    }

    public void setMgDaozhancangku(String mgDaozhancangku) {
        this.mgDaozhancangku = mgDaozhancangku == null ? null : mgDaozhancangku.trim();
    }

    public Byte getMgTihuomode() {
        return mgTihuomode;
    }

    public void setMgTihuomode(Byte mgTihuomode) {
        this.mgTihuomode = mgTihuomode;
    }

    public Byte getMgCankaoremarkprice() {
        return mgCankaoremarkprice;
    }

    public void setMgCankaoremarkprice(Byte mgCankaoremarkprice) {
        this.mgCankaoremarkprice = mgCankaoremarkprice;
    }

    public Byte getMgCanbuyother() {
        return mgCanbuyother;
    }

    public void setMgCanbuyother(Byte mgCanbuyother) {
        this.mgCanbuyother = mgCanbuyother;
    }

    public Byte getMgCanbuymaterialtype() {
        return mgCanbuymaterialtype;
    }

    public void setMgCanbuymaterialtype(Byte mgCanbuymaterialtype) {
        this.mgCanbuymaterialtype = mgCanbuymaterialtype;
    }

    public String getMgMateriallist() {
        return mgMateriallist;
    }

    public void setMgMateriallist(String mgMateriallist) {
        this.mgMateriallist = mgMateriallist == null ? null : mgMateriallist.trim();
    }

    public Byte getMgIsxieyi() {
        return mgIsxieyi;
    }

    public void setMgIsxieyi(Byte mgIsxieyi) {
        this.mgIsxieyi = mgIsxieyi;
    }

    public Double getMgXieyinum() {
        return mgXieyinum;
    }

    public void setMgXieyinum(Double mgXieyinum) {
        this.mgXieyinum = mgXieyinum;
    }

    public Double getMgXieyinum1200() {
        return mgXieyinum1200;
    }

    public void setMgXieyinum1200(Double mgXieyinum1200) {
        this.mgXieyinum1200 = mgXieyinum1200;
    }

    public Byte getMgIsfengongsi() {
        return mgIsfengongsi;
    }

    public void setMgIsfengongsi(Byte mgIsfengongsi) {
        this.mgIsfengongsi = mgIsfengongsi;
    }

    public Double getMgLockprice() {
        return mgLockprice;
    }

    public void setMgLockprice(Double mgLockprice) {
        this.mgLockprice = mgLockprice;
    }

    public Byte getMgCanuseshengyukeyres() {
        return mgCanuseshengyukeyres;
    }

    public void setMgCanuseshengyukeyres(Byte mgCanuseshengyukeyres) {
        this.mgCanuseshengyukeyres = mgCanuseshengyukeyres;
    }

    public Double getMgMonthnum() {
        return mgMonthnum;
    }

    public void setMgMonthnum(Double mgMonthnum) {
        this.mgMonthnum = mgMonthnum;
    }

    public Double getMgHetongtotalnum() {
        return mgHetongtotalnum;
    }

    public void setMgHetongtotalnum(Double mgHetongtotalnum) {
        this.mgHetongtotalnum = mgHetongtotalnum;
    }

    public String getMgCapitalpolicy() {
        return mgCapitalpolicy;
    }

    public void setMgCapitalpolicy(String mgCapitalpolicy) {
        this.mgCapitalpolicy = mgCapitalpolicy == null ? null : mgCapitalpolicy.trim();
    }

    public Byte getMgSubordinatedepartments() {
        return mgSubordinatedepartments;
    }

    public void setMgSubordinatedepartments(Byte mgSubordinatedepartments) {
        this.mgSubordinatedepartments = mgSubordinatedepartments;
    }

    public Byte getMgGcdaypricetype() {
        return mgGcdaypricetype;
    }

    public void setMgGcdaypricetype(Byte mgGcdaypricetype) {
        this.mgGcdaypricetype = mgGcdaypricetype;
    }

    public Double getMgJijiaLuowen() {
        return mgJijiaLuowen;
    }

    public void setMgJijiaLuowen(Double mgJijiaLuowen) {
        this.mgJijiaLuowen = mgJijiaLuowen;
    }

    public Double getMgJijiaGaoxian() {
        return mgJijiaGaoxian;
    }

    public void setMgJijiaGaoxian(Double mgJijiaGaoxian) {
        this.mgJijiaGaoxian = mgJijiaGaoxian;
    }

    public Double getMgJijiaPanluo() {
        return mgJijiaPanluo;
    }

    public void setMgJijiaPanluo(Double mgJijiaPanluo) {
        this.mgJijiaPanluo = mgJijiaPanluo;
    }

    public String getComdesc() {
        return comdesc;
    }

    public void setComdesc(String comdesc) {
        this.comdesc = comdesc == null ? null : comdesc.trim();
    }

}