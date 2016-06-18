package com.keeperteacher.ktservice.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.keeperteacher.ktservice.content.Content;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ktservice_video")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video extends Content {
}
