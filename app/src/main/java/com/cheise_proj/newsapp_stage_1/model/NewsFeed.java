package com.cheise_proj.newsapp_stage_1.model;

import com.cheise_proj.newsapp_stage_1.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsFeed {
    private String sectionName;
    private String webTitle;
    private String webPublicationDate;
    private String webUrl;
    private String author;
    private String thumbnail;

    public NewsFeed() {
    }

    /*format getWebPublicationDate to local date*/
    public String getFormattedDate() {
        String dateFormatted = "";
        SimpleDateFormat inputDate = new SimpleDateFormat(Constants.JAVASCRIPT_DATA_FORMAT, Locale.getDefault());
        SimpleDateFormat outputDate = new SimpleDateFormat(Constants.LOCAL_DATE_FORMAT, Locale.getDefault());
        try {
            Date newDate = inputDate.parse(getWebPublicationDate());
            assert newDate != null;
            return outputDate.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateFormatted;
        }
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    private String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
