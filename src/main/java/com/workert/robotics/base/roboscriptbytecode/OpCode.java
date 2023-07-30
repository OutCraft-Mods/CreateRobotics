package com.workert.robotics.base.roboscriptbytecode;

public interface OpCode {
	byte OP_CONSTANT = 0;
	byte OP_NULL = 1;
	byte OP_TRUE = 2;
	byte OP_FALSE = 3;
	byte OP_POP = 4;
	byte OP_GET_LOCAL = 5;
	byte OP_SET_LOCAL = 6;
	byte OP_GET_GLOBAL = 7;
	byte OP_DEFINE_GLOBAL = 8;
	byte OP_SET_GLOBAL = 9;
	byte OP_EQUAL = 10;
	byte OP_NOT_EQUAL = 11;
	byte OP_GREATER = 12;
	byte OP_GREATER_EQUAL = 13;
	byte OP_LESS = 14;
	byte OP_LESS_EQUAL = 15;
	byte OP_ADD = 16;
	byte OP_INCREMENT_GLOBAL = 17;
	byte OP_INCREMENT_LOCAL = 18;
	byte OP_INCREMENT_MAP = 19;
	byte OP_SUBTRACT = 20;
	byte OP_DECREMENT_GLOBAL = 21;
	byte OP_DECREMENT_LOCAL = 22;
	byte OP_DECREMENT_MAP = 23;
	byte OP_MULTIPLY = 24;
	byte OP_DIVIDE = 25;
	byte OP_MODULO = 26;
	byte OP_POWER = 27;
	byte OP_NOT = 28;
	byte OP_NEGATE = 29;
	byte OP_JUMP = 30;
	byte OP_JUMP_IF_FALSE = 31;
	byte OP_LOOP = 32;
	byte OP_CALL = 33;
	byte OP_GET_NATIVE = 34;
	byte OP_RETURN = 35;
	byte OP_MAKE_MAP = 36;
	byte OP_MAKE_LIST = 37;
	byte OP_GET_MAP = 38;
	byte OP_SET_MAP = 39;
	byte OP_END = 40;
}
