package com.yetoop.cloud.atlas.domain;

public class AsArtwork2WoksKey {
	
	public void create(Integer worksId, Integer artworkId) {
		this.worksId = worksId;
		this.artworkId = artworkId;
	}

	private Integer worksId;

	private Integer artworkId;

	public Integer getWorksId() {
		return worksId;
	}

	public void setWorksId(Integer worksId) {
		this.worksId = worksId;
	}

	public Integer getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(Integer artworkId) {
		this.artworkId = artworkId;
	}
}