package com.example.stranglertourreservation.tourinfo.domain.testutil;

import java.time.LocalDateTime;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class MockUtils {
	public static MockedStatic<LocalDateTime> mockNow(LocalDateTime dateTime) {
		MockedStatic<LocalDateTime> mock = Mockito.mockStatic(LocalDateTime.class);
		mock.when(LocalDateTime::now).thenReturn(dateTime);
		mock.when(() -> LocalDateTime.ofInstant(Mockito.any(), Mockito.any())).thenCallRealMethod();
		mock.when(() -> LocalDateTime.ofEpochSecond(Mockito.anyLong(), Mockito.anyInt(), Mockito.any()))
				.thenCallRealMethod();
		return mock;
	}
}
