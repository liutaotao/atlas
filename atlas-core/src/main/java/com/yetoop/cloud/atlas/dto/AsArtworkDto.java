package com.yetoop.cloud.atlas.dto;

import com.yetoop.cloud.atlas.domain.AsArtist;
import com.yetoop.cloud.atlas.domain.AsArtwork;


public class AsArtworkDto {
	
	public void create(AsArtwork artwork){
		this.id = artwork.getId();
		this.intro = artwork.getIntro();
		this.name = artwork.getName();
		this.seq = artwork.getSeq();
		this.state = artwork.getState();
		this.bigImageUrl = artwork.getBigImageUrl();
		this.smallImageUrl = artwork.getSmallImageUrl();
		this.imageUrl = artwork.getImageUrl();
		AsArtist artist = artwork.getArtist();
		if(artist != null){
			this.artistName = artist.getName();
			this.artistId = artist.getId();
		}else{
			this.artistName = artwork.getArtistName();
		}
		
		this.createYear = artwork.getCreateYear();
		this.price = artwork.getPrice();
		this.length = artwork.getLength();
		this.hight = artwork.getHight();
		this.depth = artwork.getDepth();
		this.materialId = artwork.getMaterialId();
		if(artwork.getAsMaterial() != null){
			this.materialName = artwork.getAsMaterial().getName();
		}else{
			this.materialName = artwork.getMaterial();
		}
		
		this.dimension = artwork.getDimension();
	}
	
    private Integer id;

    private String intro;

    private String name;

    private Integer seq;

    private String state;

    private String bigImageUrl;

    private String smallImageUrl;
    
    private String imageUrl;

    private String artistName;

    private Integer artistId;

    private Integer createYear;

    private Integer price;

    private Integer length;

    private Integer hight;

    private Integer depth;

    private Integer materialId;
    
    private String materialName;
    
    private String dimension;
    
	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getCreateYear() {
        return createYear;
    }

    public void setCreateYear(Integer createYear) {
        this.createYear = createYear;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getHight() {
        return hight;
    }

    public void setHight(Integer hight) {
        this.hight = hight;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

}