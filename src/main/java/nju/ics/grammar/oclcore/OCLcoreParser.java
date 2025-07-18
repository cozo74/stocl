// Generated from OCLcore.g4 by ANTLR 4.13.2

    package nju.ics.grammar.oclcore;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class OCLcoreParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, INT_LITERAL=36, REAL_LITERAL=37, 
		STRING_LITERAL=38, NULL_LITERAL=39, NEWLINE=40, LINE_COMMENT=41, WS=42, 
		SPACE=43, ID=44, CONSTANTID=45;
	public static final int
		RULE_specification = 0, RULE_invExpr = 1, RULE_inv = 2, RULE_oclBool = 3, 
		RULE_arithmeticExpr = 4, RULE_oclArithmeticValue = 5, RULE_aggOp = 6, 
		RULE_oclSet = 7, RULE_setOp = 8, RULE_oclObject = 9, RULE_oclStringValue = 10, 
		RULE_compOp = 11, RULE_varList = 12, RULE_var = 13, RULE_fRole = 14, RULE_nfRole = 15, 
		RULE_attr = 16, RULE_roleOrAttr = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"specification", "invExpr", "inv", "oclBool", "arithmeticExpr", "oclArithmeticValue", 
			"aggOp", "oclSet", "setOp", "oclObject", "oclStringValue", "compOp", 
			"varList", "var", "fRole", "nfRole", "attr", "roleOrAttr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Model'", "':'", "'context'", "'inv'", "'not'", "'and'", "'or'", 
			"'('", "')'", "'='", "'<>'", "'.'", "'-'", "'*'", "'/'", "'+'", "'->'", 
			"'()'", "'min'", "'max'", "'sum'", "'avg'", "'size'", "'.allInstances()'", 
			"'->select'", "'|'", "'->union'", "'->intersection'", "'->difference'", 
			"'self'", "'<'", "'<='", "'>='", "'>'", "','", null, null, null, "'null'", 
			null, null, null, "' '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"INT_LITERAL", "REAL_LITERAL", "STRING_LITERAL", "NULL_LITERAL", "NEWLINE", 
			"LINE_COMMENT", "WS", "SPACE", "ID", "CONSTANTID"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "OCLcore.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public OCLcoreParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SpecificationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public TerminalNode EOF() { return getToken(OCLcoreParser.EOF, 0); }
		public List<InvExprContext> invExpr() {
			return getRuleContexts(InvExprContext.class);
		}
		public InvExprContext invExpr(int i) {
			return getRuleContext(InvExprContext.class,i);
		}
		public SpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specification; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecificationContext specification() throws RecognitionException {
		SpecificationContext _localctx = new SpecificationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_specification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(T__0);
			setState(37);
			match(ID);
			setState(38);
			match(T__1);
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(39);
				invExpr();
				}
				}
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(45);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InvExprContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public List<InvContext> inv() {
			return getRuleContexts(InvContext.class);
		}
		public InvContext inv(int i) {
			return getRuleContext(InvContext.class,i);
		}
		public InvExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitInvExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InvExprContext invExpr() throws RecognitionException {
		InvExprContext _localctx = new InvExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_invExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__2);
			setState(48);
			match(ID);
			setState(50); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(49);
				inv();
				}
				}
				setState(52); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__3 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InvContext extends ParserRuleContext {
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public InvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inv; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitInv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InvContext inv() throws RecognitionException {
		InvContext _localctx = new InvContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_inv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__3);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(55);
				match(ID);
				}
			}

			setState(58);
			match(T__1);
			setState(59);
			oclBool(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolContext extends ParserRuleContext {
		public OclBoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclBool; }
	 
		public OclBoolContext() { }
		public void copyFrom(OclBoolContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolNotContext extends OclBoolContext {
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public OclBoolNotContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclBoolNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolParenContext extends OclBoolContext {
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public OclBoolParenContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclBoolParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolAndContext extends OclBoolContext {
		public List<OclBoolContext> oclBool() {
			return getRuleContexts(OclBoolContext.class);
		}
		public OclBoolContext oclBool(int i) {
			return getRuleContext(OclBoolContext.class,i);
		}
		public OclBoolAndContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclBoolAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolOrContext extends OclBoolContext {
		public List<OclBoolContext> oclBool() {
			return getRuleContexts(OclBoolContext.class);
		}
		public OclBoolContext oclBool(int i) {
			return getRuleContext(OclBoolContext.class,i);
		}
		public OclBoolOrContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclBoolOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolObjectAttrContext extends OclBoolContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public OclBoolObjectAttrContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclBoolObjectAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprStringEqualContext extends OclBoolContext {
		public Token op;
		public List<OclStringValueContext> oclStringValue() {
			return getRuleContexts(OclStringValueContext.class);
		}
		public OclStringValueContext oclStringValue(int i) {
			return getRuleContext(OclStringValueContext.class,i);
		}
		public EqualityExprStringEqualContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitEqualityExprStringEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprArithmeticContext extends OclBoolContext {
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public EqualityExprArithmeticContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitEqualityExprArithmetic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclBoolContext oclBool() throws RecognitionException {
		return oclBool(0);
	}

	private OclBoolContext oclBool(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OclBoolContext _localctx = new OclBoolContext(_ctx, _parentState);
		OclBoolContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_oclBool, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				_localctx = new OclBoolNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(62);
				match(T__4);
				setState(63);
				oclBool(7);
				}
				break;
			case 2:
				{
				_localctx = new OclBoolParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(64);
				match(T__7);
				setState(65);
				oclBool(0);
				setState(66);
				match(T__8);
				}
				break;
			case 3:
				{
				_localctx = new EqualityExprArithmeticContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(68);
				arithmeticExpr(0);
				setState(69);
				compOp();
				setState(70);
				arithmeticExpr(0);
				}
				break;
			case 4:
				{
				_localctx = new EqualityExprStringEqualContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(72);
				oclStringValue();
				setState(73);
				((EqualityExprStringEqualContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
					((EqualityExprStringEqualContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(74);
				oclStringValue();
				}
				break;
			case 5:
				{
				_localctx = new OclBoolObjectAttrContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(76);
				oclObject(0);
				setState(77);
				match(T__11);
				setState(78);
				attr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(90);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(88);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new OclBoolAndContext(new OclBoolContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBool);
						setState(82);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(83);
						match(T__5);
						setState(84);
						oclBool(7);
						}
						break;
					case 2:
						{
						_localctx = new OclBoolOrContext(new OclBoolContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBool);
						setState(85);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(86);
						match(T__6);
						setState(87);
						oclBool(6);
						}
						break;
					}
					} 
				}
				setState(92);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticExprContext extends ParserRuleContext {
		public ArithmeticExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticExpr; }
	 
		public ArithmeticExprContext() { }
		public void copyFrom(ArithmeticExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithParenContext extends ArithmeticExprContext {
		public ArithmeticExprContext arithmeticExpr() {
			return getRuleContext(ArithmeticExprContext.class,0);
		}
		public ArithParenContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithUnaryMinusContext extends ArithmeticExprContext {
		public ArithmeticExprContext arithmeticExpr() {
			return getRuleContext(ArithmeticExprContext.class,0);
		}
		public ArithUnaryMinusContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithUnaryMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithMultDivContext extends ArithmeticExprContext {
		public Token op;
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public ArithMultDivContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithMultDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithAddSubContext extends ArithmeticExprContext {
		public Token op;
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public ArithAddSubContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueContext extends ArithmeticExprContext {
		public OclArithmeticValueContext oclArithmeticValue() {
			return getRuleContext(OclArithmeticValueContext.class,0);
		}
		public ArithValueContext(ArithmeticExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticExprContext arithmeticExpr() throws RecognitionException {
		return arithmeticExpr(0);
	}

	private ArithmeticExprContext arithmeticExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArithmeticExprContext _localctx = new ArithmeticExprContext(_ctx, _parentState);
		ArithmeticExprContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_arithmeticExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
				{
				_localctx = new ArithUnaryMinusContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(94);
				match(T__12);
				setState(95);
				arithmeticExpr(5);
				}
				break;
			case T__7:
				{
				_localctx = new ArithParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(96);
				match(T__7);
				setState(97);
				arithmeticExpr(0);
				setState(98);
				match(T__8);
				}
				break;
			case T__29:
			case INT_LITERAL:
			case REAL_LITERAL:
			case STRING_LITERAL:
			case ID:
				{
				_localctx = new ArithValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(100);
				oclArithmeticValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(111);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(109);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ArithMultDivContext(new ArithmeticExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpr);
						setState(103);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(104);
						((ArithMultDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__13 || _la==T__14) ) {
							((ArithMultDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(105);
						arithmeticExpr(5);
						}
						break;
					case 2:
						{
						_localctx = new ArithAddSubContext(new ArithmeticExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpr);
						setState(106);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(107);
						((ArithAddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__12 || _la==T__15) ) {
							((ArithAddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(108);
						arithmeticExpr(4);
						}
						break;
					}
					} 
				}
				setState(113);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OclArithmeticValueContext extends ParserRuleContext {
		public OclArithmeticValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclArithmeticValue; }
	 
		public OclArithmeticValueContext() { }
		public void copyFrom(OclArithmeticValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithSetAggFuncContext extends OclArithmeticValueContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public AggOpContext aggOp() {
			return getRuleContext(AggOpContext.class,0);
		}
		public ArithSetAggFuncContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithSetAggFunc(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueAttrContext extends OclArithmeticValueContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public ArithValueAttrContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithValueAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueIntLiteralContext extends OclArithmeticValueContext {
		public TerminalNode INT_LITERAL() { return getToken(OCLcoreParser.INT_LITERAL, 0); }
		public ArithValueIntLiteralContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithValueIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueRealLiteralContext extends OclArithmeticValueContext {
		public TerminalNode REAL_LITERAL() { return getToken(OCLcoreParser.REAL_LITERAL, 0); }
		public ArithValueRealLiteralContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithValueRealLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueVarContext extends OclArithmeticValueContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ArithValueVarContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitArithValueVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclArithmeticValueContext oclArithmeticValue() throws RecognitionException {
		OclArithmeticValueContext _localctx = new OclArithmeticValueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_oclArithmeticValue);
		try {
			setState(126);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new ArithValueIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				match(INT_LITERAL);
				}
				break;
			case 2:
				_localctx = new ArithValueRealLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				match(REAL_LITERAL);
				}
				break;
			case 3:
				_localctx = new ArithValueVarContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(116);
				var();
				}
				break;
			case 4:
				_localctx = new ArithValueAttrContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(117);
				oclObject(0);
				setState(118);
				match(T__11);
				setState(119);
				attr();
				}
				break;
			case 5:
				_localctx = new ArithSetAggFuncContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(121);
				oclSet(0);
				setState(122);
				match(T__16);
				setState(123);
				aggOp();
				setState(124);
				match(T__17);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AggOpContext extends ParserRuleContext {
		public AggOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitAggOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AggOpContext aggOp() throws RecognitionException {
		AggOpContext _localctx = new AggOpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_aggOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16252928L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OclSetContext extends ParserRuleContext {
		public OclSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclSet; }
	 
		public OclSetContext() { }
		public void copyFrom(OclSetContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectSetNfRoleContext extends OclSetContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public NfRoleContext nfRole() {
			return getRuleContext(NfRoleContext.class,0);
		}
		public OclObjectSetNfRoleContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectSetNfRole(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclSetSetOpContext extends OclSetContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public SetOpContext setOp() {
			return getRuleContext(SetOpContext.class,0);
		}
		public OclSetSetOpContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclSetSetOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclSetSelectContext extends OclSetContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public OclSetSelectContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclSetSelect(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectSetAllInstancesContext extends OclSetContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public OclObjectSetAllInstancesContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectSetAllInstances(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectSetRoleOrOclValueSetAttrContext extends OclSetContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public RoleOrAttrContext roleOrAttr() {
			return getRuleContext(RoleOrAttrContext.class,0);
		}
		public OclObjectSetRoleOrOclValueSetAttrContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectSetRoleOrOclValueSetAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclSetContext oclSet() throws RecognitionException {
		return oclSet(0);
	}

	private OclSetContext oclSet(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OclSetContext _localctx = new OclSetContext(_ctx, _parentState);
		OclSetContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_oclSet, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new OclObjectSetAllInstancesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(131);
				match(ID);
				setState(132);
				match(T__23);
				}
				break;
			case 2:
				{
				_localctx = new OclObjectSetNfRoleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(133);
				oclObject(0);
				setState(134);
				match(T__11);
				setState(135);
				nfRole();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(158);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(156);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new OclObjectSetRoleOrOclValueSetAttrContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(139);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(140);
						match(T__11);
						setState(141);
						roleOrAttr();
						}
						break;
					case 2:
						{
						_localctx = new OclSetSetOpContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(142);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(143);
						setOp();
						setState(144);
						match(T__7);
						setState(145);
						oclSet(0);
						setState(146);
						match(T__8);
						}
						break;
					case 3:
						{
						_localctx = new OclSetSelectContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(148);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(149);
						match(T__24);
						setState(150);
						match(T__7);
						setState(151);
						varList();
						setState(152);
						match(T__25);
						setState(153);
						oclBool(0);
						setState(154);
						match(T__8);
						}
						break;
					}
					} 
				}
				setState(160);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SetOpContext extends ParserRuleContext {
		public SetOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitSetOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetOpContext setOp() throws RecognitionException {
		SetOpContext _localctx = new SetOpContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_setOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 939524096L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectContext extends ParserRuleContext {
		public OclObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclObject; }
	 
		public OclObjectContext() { }
		public void copyFrom(OclObjectContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectIdContext extends OclObjectContext {
		public TerminalNode STRING_LITERAL() { return getToken(OCLcoreParser.STRING_LITERAL, 0); }
		public OclObjectIdContext(OclObjectContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectSelfContext extends OclObjectContext {
		public OclObjectSelfContext(OclObjectContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectSelf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectVarContext extends OclObjectContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public OclObjectVarContext(OclObjectContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectFRoleContext extends OclObjectContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public FRoleContext fRole() {
			return getRuleContext(FRoleContext.class,0);
		}
		public OclObjectFRoleContext(OclObjectContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitOclObjectFRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclObjectContext oclObject() throws RecognitionException {
		return oclObject(0);
	}

	private OclObjectContext oclObject(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OclObjectContext _localctx = new OclObjectContext(_ctx, _parentState);
		OclObjectContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_oclObject, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				_localctx = new OclObjectVarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(164);
				var();
				}
				break;
			case T__29:
				{
				_localctx = new OclObjectSelfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(165);
				match(T__29);
				}
				break;
			case STRING_LITERAL:
				{
				_localctx = new OclObjectIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(166);
				match(STRING_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(174);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OclObjectFRoleContext(new OclObjectContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_oclObject);
					setState(169);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(170);
					match(T__11);
					setState(171);
					fRole();
					}
					} 
				}
				setState(176);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OclStringValueContext extends ParserRuleContext {
		public OclStringValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclStringValue; }
	 
		public OclStringValueContext() { }
		public void copyFrom(OclStringValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueFAttrContext extends OclStringValueContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public StringValueFAttrContext(OclStringValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitStringValueFAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueVarContext extends OclStringValueContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public StringValueVarContext(OclStringValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitStringValueVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueLiteralContext extends OclStringValueContext {
		public TerminalNode STRING_LITERAL() { return getToken(OCLcoreParser.STRING_LITERAL, 0); }
		public StringValueLiteralContext(OclStringValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitStringValueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclStringValueContext oclStringValue() throws RecognitionException {
		OclStringValueContext _localctx = new OclStringValueContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_oclStringValue);
		try {
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new StringValueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				match(STRING_LITERAL);
				}
				break;
			case 2:
				_localctx = new StringValueVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				var();
				}
				break;
			case 3:
				_localctx = new StringValueFAttrContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(179);
				oclObject(0);
				setState(180);
				match(T__11);
				setState(181);
				attr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompOpContext extends ParserRuleContext {
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitCompOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 32212257792L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarListContext extends ParserRuleContext {
		public VarListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varList; }
	 
		public VarListContext() { }
		public void copyFrom(VarListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarListValueContext extends VarListContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public VarListValueContext(VarListContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitVarListValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarListContext varList() throws RecognitionException {
		VarListContext _localctx = new VarListContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_varList);
		int _la;
		try {
			_localctx = new VarListValueContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			var();
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__34) {
				{
				{
				setState(188);
				match(T__34);
				setState(189);
				var();
				}
				}
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarContext extends ParserRuleContext {
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
	 
		public VarContext() { }
		public void copyFrom(VarContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarIDContext extends VarContext {
		public List<TerminalNode> ID() { return getTokens(OCLcoreParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(OCLcoreParser.ID, i);
		}
		public VarIDContext(VarContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitVarID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_var);
		try {
			_localctx = new VarIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(ID);
			setState(198);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(196);
				match(T__1);
				setState(197);
				match(ID);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FRoleContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public FRoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fRole; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitFRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FRoleContext fRole() throws RecognitionException {
		FRoleContext _localctx = new FRoleContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_fRole);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NfRoleContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public NfRoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nfRole; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitNfRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NfRoleContext nfRole() throws RecognitionException {
		NfRoleContext _localctx = new NfRoleContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_nfRole);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttrContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public AttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttrContext attr() throws RecognitionException {
		AttrContext _localctx = new AttrContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_attr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RoleOrAttrContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OCLcoreParser.ID, 0); }
		public RoleOrAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_roleOrAttr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OCLcoreVisitor ) return ((OCLcoreVisitor<? extends T>)visitor).visitRoleOrAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoleOrAttrContext roleOrAttr() throws RecognitionException {
		RoleOrAttrContext _localctx = new RoleOrAttrContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_roleOrAttr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return oclBool_sempred((OclBoolContext)_localctx, predIndex);
		case 4:
			return arithmeticExpr_sempred((ArithmeticExprContext)_localctx, predIndex);
		case 7:
			return oclSet_sempred((OclSetContext)_localctx, predIndex);
		case 9:
			return oclObject_sempred((OclObjectContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean oclBool_sempred(OclBoolContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean arithmeticExpr_sempred(ArithmeticExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean oclSet_sempred(OclSetContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 2);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean oclObject_sempred(OclObjectContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001-\u00d1\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000)\b\u0000\n\u0000\f\u0000,\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0004\u0001"+
		"3\b\u0001\u000b\u0001\f\u00014\u0001\u0002\u0001\u0002\u0003\u00029\b"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003Q\b"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003Y\b\u0003\n\u0003\f\u0003\\\t\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0003\u0004f\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0005\u0004n\b\u0004\n\u0004\f\u0004q\t"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005\u007f\b\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\u008a\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007\u009d\b\u0007\n\u0007\f\u0007\u00a0\t\u0007\u0001\b"+
		"\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00a8\b\t\u0001\t\u0001"+
		"\t\u0001\t\u0005\t\u00ad\b\t\n\t\f\t\u00b0\t\t\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0003\n\u00b8\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0005\f\u00bf\b\f\n\f\f\f\u00c2\t\f\u0001\r\u0001\r"+
		"\u0001\r\u0003\r\u00c7\b\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0000\u0004"+
		"\u0006\b\u000e\u0012\u0012\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"\u0000\u0006\u0001\u0000\n\u000b"+
		"\u0001\u0000\u000e\u000f\u0002\u0000\r\r\u0010\u0010\u0001\u0000\u0013"+
		"\u0017\u0001\u0000\u001b\u001d\u0002\u0000\n\u000b\u001f\"\u00da\u0000"+
		"$\u0001\u0000\u0000\u0000\u0002/\u0001\u0000\u0000\u0000\u00046\u0001"+
		"\u0000\u0000\u0000\u0006P\u0001\u0000\u0000\u0000\be\u0001\u0000\u0000"+
		"\u0000\n~\u0001\u0000\u0000\u0000\f\u0080\u0001\u0000\u0000\u0000\u000e"+
		"\u0089\u0001\u0000\u0000\u0000\u0010\u00a1\u0001\u0000\u0000\u0000\u0012"+
		"\u00a7\u0001\u0000\u0000\u0000\u0014\u00b7\u0001\u0000\u0000\u0000\u0016"+
		"\u00b9\u0001\u0000\u0000\u0000\u0018\u00bb\u0001\u0000\u0000\u0000\u001a"+
		"\u00c3\u0001\u0000\u0000\u0000\u001c\u00c8\u0001\u0000\u0000\u0000\u001e"+
		"\u00ca\u0001\u0000\u0000\u0000 \u00cc\u0001\u0000\u0000\u0000\"\u00ce"+
		"\u0001\u0000\u0000\u0000$%\u0005\u0001\u0000\u0000%&\u0005,\u0000\u0000"+
		"&*\u0005\u0002\u0000\u0000\')\u0003\u0002\u0001\u0000(\'\u0001\u0000\u0000"+
		"\u0000),\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000*+\u0001\u0000"+
		"\u0000\u0000+-\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000-.\u0005"+
		"\u0000\u0000\u0001.\u0001\u0001\u0000\u0000\u0000/0\u0005\u0003\u0000"+
		"\u000002\u0005,\u0000\u000013\u0003\u0004\u0002\u000021\u0001\u0000\u0000"+
		"\u000034\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001\u0000"+
		"\u0000\u00005\u0003\u0001\u0000\u0000\u000068\u0005\u0004\u0000\u0000"+
		"79\u0005,\u0000\u000087\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u0000"+
		"9:\u0001\u0000\u0000\u0000:;\u0005\u0002\u0000\u0000;<\u0003\u0006\u0003"+
		"\u0000<\u0005\u0001\u0000\u0000\u0000=>\u0006\u0003\uffff\uffff\u0000"+
		">?\u0005\u0005\u0000\u0000?Q\u0003\u0006\u0003\u0007@A\u0005\b\u0000\u0000"+
		"AB\u0003\u0006\u0003\u0000BC\u0005\t\u0000\u0000CQ\u0001\u0000\u0000\u0000"+
		"DE\u0003\b\u0004\u0000EF\u0003\u0016\u000b\u0000FG\u0003\b\u0004\u0000"+
		"GQ\u0001\u0000\u0000\u0000HI\u0003\u0014\n\u0000IJ\u0007\u0000\u0000\u0000"+
		"JK\u0003\u0014\n\u0000KQ\u0001\u0000\u0000\u0000LM\u0003\u0012\t\u0000"+
		"MN\u0005\f\u0000\u0000NO\u0003 \u0010\u0000OQ\u0001\u0000\u0000\u0000"+
		"P=\u0001\u0000\u0000\u0000P@\u0001\u0000\u0000\u0000PD\u0001\u0000\u0000"+
		"\u0000PH\u0001\u0000\u0000\u0000PL\u0001\u0000\u0000\u0000QZ\u0001\u0000"+
		"\u0000\u0000RS\n\u0006\u0000\u0000ST\u0005\u0006\u0000\u0000TY\u0003\u0006"+
		"\u0003\u0007UV\n\u0005\u0000\u0000VW\u0005\u0007\u0000\u0000WY\u0003\u0006"+
		"\u0003\u0006XR\u0001\u0000\u0000\u0000XU\u0001\u0000\u0000\u0000Y\\\u0001"+
		"\u0000\u0000\u0000ZX\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000"+
		"[\u0007\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000]^\u0006\u0004"+
		"\uffff\uffff\u0000^_\u0005\r\u0000\u0000_f\u0003\b\u0004\u0005`a\u0005"+
		"\b\u0000\u0000ab\u0003\b\u0004\u0000bc\u0005\t\u0000\u0000cf\u0001\u0000"+
		"\u0000\u0000df\u0003\n\u0005\u0000e]\u0001\u0000\u0000\u0000e`\u0001\u0000"+
		"\u0000\u0000ed\u0001\u0000\u0000\u0000fo\u0001\u0000\u0000\u0000gh\n\u0004"+
		"\u0000\u0000hi\u0007\u0001\u0000\u0000in\u0003\b\u0004\u0005jk\n\u0003"+
		"\u0000\u0000kl\u0007\u0002\u0000\u0000ln\u0003\b\u0004\u0004mg\u0001\u0000"+
		"\u0000\u0000mj\u0001\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001"+
		"\u0000\u0000\u0000op\u0001\u0000\u0000\u0000p\t\u0001\u0000\u0000\u0000"+
		"qo\u0001\u0000\u0000\u0000r\u007f\u0005$\u0000\u0000s\u007f\u0005%\u0000"+
		"\u0000t\u007f\u0003\u001a\r\u0000uv\u0003\u0012\t\u0000vw\u0005\f\u0000"+
		"\u0000wx\u0003 \u0010\u0000x\u007f\u0001\u0000\u0000\u0000yz\u0003\u000e"+
		"\u0007\u0000z{\u0005\u0011\u0000\u0000{|\u0003\f\u0006\u0000|}\u0005\u0012"+
		"\u0000\u0000}\u007f\u0001\u0000\u0000\u0000~r\u0001\u0000\u0000\u0000"+
		"~s\u0001\u0000\u0000\u0000~t\u0001\u0000\u0000\u0000~u\u0001\u0000\u0000"+
		"\u0000~y\u0001\u0000\u0000\u0000\u007f\u000b\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0007\u0003\u0000\u0000\u0081\r\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0006\u0007\uffff\uffff\u0000\u0083\u0084\u0005,\u0000\u0000\u0084\u008a"+
		"\u0005\u0018\u0000\u0000\u0085\u0086\u0003\u0012\t\u0000\u0086\u0087\u0005"+
		"\f\u0000\u0000\u0087\u0088\u0003\u001e\u000f\u0000\u0088\u008a\u0001\u0000"+
		"\u0000\u0000\u0089\u0082\u0001\u0000\u0000\u0000\u0089\u0085\u0001\u0000"+
		"\u0000\u0000\u008a\u009e\u0001\u0000\u0000\u0000\u008b\u008c\n\u0004\u0000"+
		"\u0000\u008c\u008d\u0005\f\u0000\u0000\u008d\u009d\u0003\"\u0011\u0000"+
		"\u008e\u008f\n\u0002\u0000\u0000\u008f\u0090\u0003\u0010\b\u0000\u0090"+
		"\u0091\u0005\b\u0000\u0000\u0091\u0092\u0003\u000e\u0007\u0000\u0092\u0093"+
		"\u0005\t\u0000\u0000\u0093\u009d\u0001\u0000\u0000\u0000\u0094\u0095\n"+
		"\u0001\u0000\u0000\u0095\u0096\u0005\u0019\u0000\u0000\u0096\u0097\u0005"+
		"\b\u0000\u0000\u0097\u0098\u0003\u0018\f\u0000\u0098\u0099\u0005\u001a"+
		"\u0000\u0000\u0099\u009a\u0003\u0006\u0003\u0000\u009a\u009b\u0005\t\u0000"+
		"\u0000\u009b\u009d\u0001\u0000\u0000\u0000\u009c\u008b\u0001\u0000\u0000"+
		"\u0000\u009c\u008e\u0001\u0000\u0000\u0000\u009c\u0094\u0001\u0000\u0000"+
		"\u0000\u009d\u00a0\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000\u0000"+
		"\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u000f\u0001\u0000\u0000"+
		"\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a1\u00a2\u0007\u0004\u0000"+
		"\u0000\u00a2\u0011\u0001\u0000\u0000\u0000\u00a3\u00a4\u0006\t\uffff\uffff"+
		"\u0000\u00a4\u00a8\u0003\u001a\r\u0000\u00a5\u00a8\u0005\u001e\u0000\u0000"+
		"\u00a6\u00a8\u0005&\u0000\u0000\u00a7\u00a3\u0001\u0000\u0000\u0000\u00a7"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a6\u0001\u0000\u0000\u0000\u00a8"+
		"\u00ae\u0001\u0000\u0000\u0000\u00a9\u00aa\n\u0004\u0000\u0000\u00aa\u00ab"+
		"\u0005\f\u0000\u0000\u00ab\u00ad\u0003\u001c\u000e\u0000\u00ac\u00a9\u0001"+
		"\u0000\u0000\u0000\u00ad\u00b0\u0001\u0000\u0000\u0000\u00ae\u00ac\u0001"+
		"\u0000\u0000\u0000\u00ae\u00af\u0001\u0000\u0000\u0000\u00af\u0013\u0001"+
		"\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b1\u00b8\u0005"+
		"&\u0000\u0000\u00b2\u00b8\u0003\u001a\r\u0000\u00b3\u00b4\u0003\u0012"+
		"\t\u0000\u00b4\u00b5\u0005\f\u0000\u0000\u00b5\u00b6\u0003 \u0010\u0000"+
		"\u00b6\u00b8\u0001\u0000\u0000\u0000\u00b7\u00b1\u0001\u0000\u0000\u0000"+
		"\u00b7\u00b2\u0001\u0000\u0000\u0000\u00b7\u00b3\u0001\u0000\u0000\u0000"+
		"\u00b8\u0015\u0001\u0000\u0000\u0000\u00b9\u00ba\u0007\u0005\u0000\u0000"+
		"\u00ba\u0017\u0001\u0000\u0000\u0000\u00bb\u00c0\u0003\u001a\r\u0000\u00bc"+
		"\u00bd\u0005#\u0000\u0000\u00bd\u00bf\u0003\u001a\r\u0000\u00be\u00bc"+
		"\u0001\u0000\u0000\u0000\u00bf\u00c2\u0001\u0000\u0000\u0000\u00c0\u00be"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000\u0000\u0000\u00c1\u0019"+
		"\u0001\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000\u0000\u0000\u00c3\u00c6"+
		"\u0005,\u0000\u0000\u00c4\u00c5\u0005\u0002\u0000\u0000\u00c5\u00c7\u0005"+
		",\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001\u0000"+
		"\u0000\u0000\u00c7\u001b\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005,\u0000"+
		"\u0000\u00c9\u001d\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005,\u0000\u0000"+
		"\u00cb\u001f\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005,\u0000\u0000\u00cd"+
		"!\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005,\u0000\u0000\u00cf#\u0001"+
		"\u0000\u0000\u0000\u0012*48PXZemo~\u0089\u009c\u009e\u00a7\u00ae\u00b7"+
		"\u00c0\u00c6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}