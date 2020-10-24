package lab6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNodeStacking {

	@Test
	void test() {
		assertEquals(128, NodeStacking.weightSupporting(0, 0));
		assertEquals(-1, NodeStacking.weightSupporting(0, 1));
		assertEquals(64, NodeStacking.weightSupporting(1, 1));
		assertEquals(20, NodeStacking.weightSupporting(5, 1));
		assertEquals(48, NodeStacking.weightSupporting(4, 2));
		assertEquals(40, NodeStacking.weightSupporting(5, 2));
		assertEquals(30, NodeStacking.weightSupporting(6, 2));
	}

}
