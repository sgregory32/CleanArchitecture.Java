package com.clean.architecture.api;

import org.springframework.stereotype.Component;

@Component
public class ApiApplicationProperties {

	private String environmentName;
	private String corsDomainWhiteList;
	private String baseApiUrl;
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
	public String getCorsDomainWhiteList() {
		return corsDomainWhiteList;
	}
	public void setCorsDomainWhiteList(String corsDomainWhiteList) {
		this.corsDomainWhiteList = corsDomainWhiteList;
	}
	public String getBaseApiUrl() {
		return baseApiUrl;
	}
	public void setBaseApiUrl(String baseApiUrl) {
		this.baseApiUrl = baseApiUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseApiUrl == null) ? 0 : baseApiUrl.hashCode());
		result = prime * result + ((corsDomainWhiteList == null) ? 0 : corsDomainWhiteList.hashCode());
		result = prime * result + ((environmentName == null) ? 0 : environmentName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiApplicationProperties other = (ApiApplicationProperties) obj;
		if (baseApiUrl == null) {
			if (other.baseApiUrl != null)
				return false;
		} else if (!baseApiUrl.equals(other.baseApiUrl))
			return false;
		if (corsDomainWhiteList == null) {
			if (other.corsDomainWhiteList != null)
				return false;
		} else if (!corsDomainWhiteList.equals(other.corsDomainWhiteList))
			return false;
		if (environmentName == null) {
			if (other.environmentName != null)
				return false;
		} else if (!environmentName.equals(other.environmentName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ApiApplicationProperties [environmentName=" + environmentName + ", corsDomainWhiteList="
				+ corsDomainWhiteList + ", baseApiUrl=" + baseApiUrl + "]";
	}
}
