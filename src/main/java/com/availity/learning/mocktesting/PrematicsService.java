package com.availity.learning.mocktesting;

public interface PrematicsService {

  void registerOrganization(String organization) throws PrematicsException;

  void registerUser(String user) throws PrematicsException;

  void recordServiceUsage() throws PrematicsException;
}
