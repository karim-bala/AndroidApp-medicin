package com.example.medicineapp;

public class MedicinModel {
    String id="";
    String title="";
    String commercial="";
    String bare_code="";
    String created_at="";
    String date_fab="";
    String labo="";
    String denom="";
    String form="";
    String duree="";
    String rembousable="";
    String lot="";
    String date_peremp="";
    String descrip="";
    String prix="";
    String qnt="";


    public MedicinModel(String id, String commercial,String bare_code,String title,String created_at,String date_fab,String prix,String descrip,
                        String qnt,String date_peremp,String lot,String rembousable,String duree, String form, String labo , String denom) {
        this.id = id;
        this.title = title;
        this.commercial = commercial;
        this.bare_code = bare_code;
        this.date_fab=date_fab;
        this.created_at=created_at;
        this.labo=labo;
        this.denom=denom;
        this.form=form;
        this.duree=duree;
        this.rembousable=rembousable;
        this.lot=lot;
        this.date_peremp=date_peremp;
        this.descrip=descrip;
        this.prix=prix;
        this.qnt=qnt;

    }

    public MedicinModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommercial() {
        return commercial;
    }

    public void setAuthor(String commercial) {
        this.commercial = commercial;
    }

    public String getBare_code() {
        return bare_code;
    }

    public void setBare_code(String bare_code) {
        this.bare_code = bare_code;
    }

    public String getDate_fab() {
        return date_fab;
    }

    public void setDate_fab(String date_fab) {
        this.date_fab = date_fab;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLabo() {
        return labo;
    }

    public void setLabo(String labo) {
        this.labo = labo;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getRembousable() {
        return rembousable;
    }

    public void setRembousable(String rembousable) {
        this.rembousable = rembousable;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getDate_peremp() {
        return date_peremp;
    }

    public void setDate_peremp(String date_peremp) {
        this.date_peremp = date_peremp;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }





}