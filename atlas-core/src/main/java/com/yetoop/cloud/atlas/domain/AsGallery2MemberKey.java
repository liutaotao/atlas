package com.yetoop.cloud.atlas.domain;

public class AsGallery2MemberKey {
    private Integer galleryId;

    private Integer memberId;
    
    private AsGallary asGallary;

    public AsGallary getAsGallary() {
		return asGallary;
	}

	public void setAsGallary(AsGallary asGallary) {
		this.asGallary = asGallary;
	}

	public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}