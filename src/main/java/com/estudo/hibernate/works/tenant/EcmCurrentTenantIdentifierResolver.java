package com.estudo.hibernate.works.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class EcmCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	private static ThreadLocal<String> tl = new ThreadLocal<>();

	public static void setTenantIdentifier(String tenantId) {
		tl.set(tenantId);
	}

	@Override
	public String resolveCurrentTenantIdentifier() {
		// TODO Auto-generated method stub
		return tl.get();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return false;
	}

}
