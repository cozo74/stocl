// Generated from STOCL.g4 by ANTLR 4.13.2

    package nju.ics.grammar.stocl;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class STOCLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, INT_LITERAL=49, REAL_LITERAL=50, STRING_LITERAL=51, 
		NULL_LITERAL=52, NEWLINE=53, LINE_COMMENT=54, WS=55, SPACE=56, ID=57, 
		CONSTANTID=58;
	public static final int
		RULE_specification = 0, RULE_invExpr = 1, RULE_inv = 2, RULE_oclBool = 3, 
		RULE_setBoolFunc = 4, RULE_equalityExpr = 5, RULE_arithmeticExpr = 6, 
		RULE_oclSet = 7, RULE_oclObject = 8, RULE_oclArithmeticValue = 9, RULE_oclStringValue = 10, 
		RULE_boolOp = 11, RULE_compOp = 12, RULE_varList = 13, RULE_var = 14, 
		RULE_roleOrAttr = 15, RULE_fRole = 16, RULE_nfRole = 17, RULE_attr = 18, 
		RULE_bAttr = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"specification", "invExpr", "inv", "oclBool", "setBoolFunc", "equalityExpr", 
			"arithmeticExpr", "oclSet", "oclObject", "oclArithmeticValue", "oclStringValue", 
			"boolOp", "compOp", "varList", "var", "roleOrAttr", "fRole", "nfRole", 
			"attr", "bAttr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Model'", "':'", "'context'", "'inv'", "'not'", "'.'", "'('", 
			"')'", "'->includesAll('", "'->excludesAll('", "'->includes('", "'->excludes('", 
			"'->forAll('", "'|'", "'->exists('", "'->isEmpty()'", "'->notEmpty()'", 
			"'->one('", "'->isUnique('", "'='", "'<>'", "'-'", "'*'", "'/'", "'+'", 
			"'->union('", "'->intersection('", "'->symmetricDifference('", "'->difference('", 
			"'->select('", "'->reject('", "'::'", "'allInstances()'", "'self'", "'->min()'", 
			"'->max()'", "'->size()'", "'->sum()'", "'->avg()'", "'and'", "'or'", 
			"'xor'", "'implies'", "'<'", "'<='", "'>='", "'>'", "','", null, null, 
			null, "'null'", null, null, null, "' '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "INT_LITERAL", "REAL_LITERAL", "STRING_LITERAL", "NULL_LITERAL", 
			"NEWLINE", "LINE_COMMENT", "WS", "SPACE", "ID", "CONSTANTID"
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
	public String getGrammarFileName() { return "STOCL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public STOCLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SpecificationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public TerminalNode EOF() { return getToken(STOCLParser.EOF, 0); }
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSpecification(this);
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
			setState(40);
			match(T__0);
			setState(41);
			match(ID);
			setState(42);
			match(T__1);
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(43);
				invExpr();
				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(49);
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
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitInvExpr(this);
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
			setState(51);
			match(T__2);
			setState(52);
			match(ID);
			setState(54); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(53);
				inv();
				}
				}
				setState(56); 
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
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public InvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inv; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitInv(this);
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
			setState(58);
			match(T__3);
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(59);
				match(ID);
				}
			}

			setState(62);
			match(T__1);
			setState(63);
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
	public static class OclBoolBoolOpContext extends OclBoolContext {
		public List<OclBoolContext> oclBool() {
			return getRuleContexts(OclBoolContext.class);
		}
		public OclBoolContext oclBool(int i) {
			return getRuleContext(OclBoolContext.class,i);
		}
		public BoolOpContext boolOp() {
			return getRuleContext(BoolOpContext.class,0);
		}
		public OclBoolBoolOpContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolBoolOp(this);
			else return visitor.visitChildren(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolNot(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolSetBoolFuncContext extends OclBoolContext {
		public SetBoolFuncContext setBoolFunc() {
			return getRuleContext(SetBoolFuncContext.class,0);
		}
		public OclBoolSetBoolFuncContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolSetBoolFunc(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolEqualityExprContext extends OclBoolContext {
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public OclBoolEqualityExprContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolBAttrContext extends OclBoolContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public BAttrContext bAttr() {
			return getRuleContext(BAttrContext.class,0);
		}
		public OclBoolBAttrContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolBAttr(this);
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				_localctx = new OclBoolNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(66);
				match(T__4);
				setState(67);
				oclBool(6);
				}
				break;
			case 2:
				{
				_localctx = new OclBoolEqualityExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(68);
				equalityExpr();
				}
				break;
			case 3:
				{
				_localctx = new OclBoolBAttrContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(69);
				oclObject(0);
				setState(70);
				match(T__5);
				setState(71);
				bAttr();
				}
				break;
			case 4:
				{
				_localctx = new OclBoolSetBoolFuncContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(73);
				setBoolFunc();
				}
				break;
			case 5:
				{
				_localctx = new OclBoolParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(74);
				match(T__6);
				setState(75);
				oclBool(0);
				setState(76);
				match(T__7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(86);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OclBoolBoolOpContext(new OclBoolContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_oclBool);
					setState(80);
					if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
					setState(81);
					boolOp();
					setState(82);
					oclBool(6);
					}
					} 
				}
				setState(88);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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
	public static class SetBoolFuncContext extends ParserRuleContext {
		public SetBoolFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setBoolFunc; }
	 
		public SetBoolFuncContext() { }
		public void copyFrom(SetBoolFuncContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncExistsContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public SetBoolFuncExistsContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncExists(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncIsEmptyContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public SetBoolFuncIsEmptyContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncIsEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncIncludesAllContext extends SetBoolFuncContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public SetBoolFuncIncludesAllContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncIncludesAll(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncExcludesAllContext extends SetBoolFuncContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public SetBoolFuncExcludesAllContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncExcludesAll(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncIncludesContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public SetBoolFuncIncludesContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncIncludes(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncNotEmptyContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public SetBoolFuncNotEmptyContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncNotEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncIsUniqueContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public SetBoolFuncIsUniqueContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncIsUnique(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncForAllContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public SetBoolFuncForAllContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncForAll(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncExcludesContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public SetBoolFuncExcludesContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncExcludes(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetBoolFuncOneContext extends SetBoolFuncContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public SetBoolFuncOneContext(SetBoolFuncContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetBoolFuncOne(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetBoolFuncContext setBoolFunc() throws RecognitionException {
		SetBoolFuncContext _localctx = new SetBoolFuncContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_setBoolFunc);
		try {
			setState(141);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new SetBoolFuncIncludesAllContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				oclSet(0);
				setState(90);
				match(T__8);
				setState(91);
				oclSet(0);
				setState(92);
				match(T__7);
				}
				break;
			case 2:
				_localctx = new SetBoolFuncExcludesAllContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				oclSet(0);
				setState(95);
				match(T__9);
				setState(96);
				oclSet(0);
				setState(97);
				match(T__7);
				}
				break;
			case 3:
				_localctx = new SetBoolFuncIncludesContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
				oclSet(0);
				setState(100);
				match(T__10);
				setState(101);
				oclObject(0);
				setState(102);
				match(T__7);
				}
				break;
			case 4:
				_localctx = new SetBoolFuncExcludesContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(104);
				oclSet(0);
				setState(105);
				match(T__11);
				setState(106);
				oclObject(0);
				setState(107);
				match(T__7);
				}
				break;
			case 5:
				_localctx = new SetBoolFuncForAllContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(109);
				oclSet(0);
				setState(110);
				match(T__12);
				setState(111);
				varList();
				setState(112);
				match(T__13);
				setState(113);
				oclBool(0);
				setState(114);
				match(T__7);
				}
				break;
			case 6:
				_localctx = new SetBoolFuncExistsContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(116);
				oclSet(0);
				setState(117);
				match(T__14);
				setState(118);
				varList();
				setState(119);
				match(T__13);
				setState(120);
				oclBool(0);
				setState(121);
				match(T__7);
				}
				break;
			case 7:
				_localctx = new SetBoolFuncIsEmptyContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(123);
				oclSet(0);
				setState(124);
				match(T__15);
				}
				break;
			case 8:
				_localctx = new SetBoolFuncNotEmptyContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(126);
				oclSet(0);
				setState(127);
				match(T__16);
				}
				break;
			case 9:
				_localctx = new SetBoolFuncOneContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(129);
				oclSet(0);
				setState(130);
				match(T__17);
				setState(131);
				varList();
				setState(132);
				match(T__13);
				setState(133);
				oclBool(0);
				setState(134);
				match(T__7);
				}
				break;
			case 10:
				_localctx = new SetBoolFuncIsUniqueContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(136);
				oclSet(0);
				setState(137);
				match(T__18);
				setState(138);
				attr();
				setState(139);
				match(T__7);
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
	public static class EqualityExprContext extends ParserRuleContext {
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
	 
		public EqualityExprContext() { }
		public void copyFrom(EqualityExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprStringEqualContext extends EqualityExprContext {
		public List<OclStringValueContext> oclStringValue() {
			return getRuleContexts(OclStringValueContext.class);
		}
		public OclStringValueContext oclStringValue(int i) {
			return getRuleContext(OclStringValueContext.class,i);
		}
		public EqualityExprStringEqualContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprStringEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprArithmeticContext extends EqualityExprContext {
		public List<ArithmeticExprContext> arithmeticExpr() {
			return getRuleContexts(ArithmeticExprContext.class);
		}
		public ArithmeticExprContext arithmeticExpr(int i) {
			return getRuleContext(ArithmeticExprContext.class,i);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public EqualityExprArithmeticContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprArithmetic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprStringNotEqualContext extends EqualityExprContext {
		public List<OclStringValueContext> oclStringValue() {
			return getRuleContexts(OclStringValueContext.class);
		}
		public OclStringValueContext oclStringValue(int i) {
			return getRuleContext(OclStringValueContext.class,i);
		}
		public EqualityExprStringNotEqualContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprStringNotEqual(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_equalityExpr);
		try {
			setState(155);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new EqualityExprArithmeticContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				arithmeticExpr(0);
				setState(144);
				compOp();
				setState(145);
				arithmeticExpr(0);
				}
				break;
			case 2:
				_localctx = new EqualityExprStringEqualContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(147);
				oclStringValue();
				setState(148);
				match(T__19);
				setState(149);
				oclStringValue();
				}
				break;
			case 3:
				_localctx = new EqualityExprStringNotEqualContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(151);
				oclStringValue();
				setState(152);
				match(T__20);
				setState(153);
				oclStringValue();
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithParen(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithUnaryMinus(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithMultDiv(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithAddSub(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValue(this);
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
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_arithmeticExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__21:
				{
				_localctx = new ArithUnaryMinusContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(158);
				match(T__21);
				setState(159);
				arithmeticExpr(5);
				}
				break;
			case T__6:
				{
				_localctx = new ArithParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(160);
				match(T__6);
				setState(161);
				arithmeticExpr(0);
				setState(162);
				match(T__7);
				}
				break;
			case T__33:
			case INT_LITERAL:
			case REAL_LITERAL:
			case STRING_LITERAL:
			case ID:
				{
				_localctx = new ArithValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(164);
				oclArithmeticValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(175);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(173);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ArithMultDivContext(new ArithmeticExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpr);
						setState(167);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(168);
						((ArithMultDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__22 || _la==T__23) ) {
							((ArithMultDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(169);
						arithmeticExpr(5);
						}
						break;
					case 2:
						{
						_localctx = new ArithAddSubContext(new ArithmeticExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpr);
						setState(170);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(171);
						((ArithAddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__21 || _la==T__24) ) {
							((ArithAddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(172);
						arithmeticExpr(4);
						}
						break;
					}
					} 
				}
				setState(177);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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
	public static class IntersectionContext extends OclSetContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public IntersectionContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitIntersection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RejectContext extends OclSetContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public RejectContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitReject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetRoleOrAttrContext extends OclSetContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public RoleOrAttrContext roleOrAttr() {
			return getRuleContext(RoleOrAttrContext.class,0);
		}
		public SetRoleOrAttrContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSetRoleOrAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectContext extends OclSetContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public SelectContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSelect(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SymmetricDifferenceContext extends OclSetContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public SymmetricDifferenceContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSymmetricDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NfRoleAndRoleContext extends OclSetContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public NfRoleContext nfRole() {
			return getRuleContext(NfRoleContext.class,0);
		}
		public NfRoleAndRoleContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitNfRoleAndRole(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DifferenceContext extends OclSetContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public DifferenceContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AllInstancesContext extends OclSetContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public AllInstancesContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitAllInstances(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnionContext extends OclSetContext {
		public List<OclSetContext> oclSet() {
			return getRuleContexts(OclSetContext.class);
		}
		public OclSetContext oclSet(int i) {
			return getRuleContext(OclSetContext.class,i);
		}
		public UnionContext(OclSetContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitUnion(this);
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
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new NfRoleAndRoleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(179);
				oclObject(0);
				setState(180);
				match(T__5);
				setState(181);
				nfRole();
				}
				break;
			case 2:
				{
				_localctx = new AllInstancesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(183);
				match(ID);
				setState(184);
				_la = _input.LA(1);
				if ( !(_la==T__5 || _la==T__31) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(185);
				match(T__32);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(227);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(225);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new UnionContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(188);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(189);
						match(T__25);
						setState(190);
						oclSet(0);
						setState(191);
						match(T__7);
						}
						break;
					case 2:
						{
						_localctx = new IntersectionContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(193);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(194);
						match(T__26);
						setState(195);
						oclSet(0);
						setState(196);
						match(T__7);
						}
						break;
					case 3:
						{
						_localctx = new SymmetricDifferenceContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(198);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(199);
						match(T__27);
						setState(200);
						oclSet(0);
						setState(201);
						match(T__7);
						}
						break;
					case 4:
						{
						_localctx = new DifferenceContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(203);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(204);
						match(T__28);
						setState(205);
						oclSet(0);
						setState(206);
						match(T__7);
						}
						break;
					case 5:
						{
						_localctx = new SelectContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(208);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(209);
						match(T__29);
						setState(210);
						varList();
						setState(211);
						match(T__13);
						setState(212);
						oclBool(0);
						setState(213);
						match(T__7);
						}
						break;
					case 6:
						{
						_localctx = new RejectContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(215);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(216);
						match(T__30);
						setState(217);
						varList();
						setState(218);
						match(T__13);
						setState(219);
						oclBool(0);
						setState(220);
						match(T__7);
						}
						break;
					case 7:
						{
						_localctx = new SetRoleOrAttrContext(new OclSetContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclSet);
						setState(222);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(223);
						match(T__5);
						setState(224);
						roleOrAttr();
						}
						break;
					}
					} 
				}
				setState(229);
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
		public TerminalNode STRING_LITERAL() { return getToken(STOCLParser.STRING_LITERAL, 0); }
		public OclObjectIdContext(OclObjectContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectSelfContext extends OclObjectContext {
		public OclObjectSelfContext(OclObjectContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectSelf(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectVar(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectFRole(this);
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
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_oclObject, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				_localctx = new OclObjectVarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(231);
				var();
				}
				break;
			case T__33:
				{
				_localctx = new OclObjectSelfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(232);
				match(T__33);
				}
				break;
			case STRING_LITERAL:
				{
				_localctx = new OclObjectIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(233);
				match(STRING_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(241);
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
					setState(236);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(237);
					match(T__5);
					setState(238);
					fRole();
					}
					} 
				}
				setState(243);
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
	public static class ArithValueMaxContext extends OclArithmeticValueContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public ArithValueMaxContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueMax(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueMinContext extends OclArithmeticValueContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public ArithValueMinContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueMin(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueAvgContext extends OclArithmeticValueContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public ArithValueAvgContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueAvg(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueFAttrContext extends OclArithmeticValueContext {
		public OclObjectContext oclObject() {
			return getRuleContext(OclObjectContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public ArithValueFAttrContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueFAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueIntLiteralContext extends OclArithmeticValueContext {
		public TerminalNode INT_LITERAL() { return getToken(STOCLParser.INT_LITERAL, 0); }
		public ArithValueIntLiteralContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueRealLiteralContext extends OclArithmeticValueContext {
		public TerminalNode REAL_LITERAL() { return getToken(STOCLParser.REAL_LITERAL, 0); }
		public ArithValueRealLiteralContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueRealLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueSumContext extends OclArithmeticValueContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public ArithValueSumContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueSum(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueSizeContext extends OclArithmeticValueContext {
		public OclSetContext oclSet() {
			return getRuleContext(OclSetContext.class,0);
		}
		public ArithValueSizeContext(OclArithmeticValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueSize(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclArithmeticValueContext oclArithmeticValue() throws RecognitionException {
		OclArithmeticValueContext _localctx = new OclArithmeticValueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_oclArithmeticValue);
		try {
			setState(266);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new ArithValueIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				match(INT_LITERAL);
				}
				break;
			case 2:
				_localctx = new ArithValueRealLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				match(REAL_LITERAL);
				}
				break;
			case 3:
				_localctx = new ArithValueVarContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(246);
				var();
				}
				break;
			case 4:
				_localctx = new ArithValueFAttrContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(247);
				oclObject(0);
				setState(248);
				match(T__5);
				setState(249);
				attr();
				}
				break;
			case 5:
				_localctx = new ArithValueMinContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(251);
				oclSet(0);
				setState(252);
				match(T__34);
				}
				break;
			case 6:
				_localctx = new ArithValueMaxContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(254);
				oclSet(0);
				setState(255);
				match(T__35);
				}
				break;
			case 7:
				_localctx = new ArithValueSizeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(257);
				oclSet(0);
				setState(258);
				match(T__36);
				}
				break;
			case 8:
				_localctx = new ArithValueSumContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(260);
				oclSet(0);
				setState(261);
				match(T__37);
				}
				break;
			case 9:
				_localctx = new ArithValueAvgContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(263);
				oclSet(0);
				setState(264);
				match(T__38);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueFAttr(this);
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueLiteralContext extends OclStringValueContext {
		public TerminalNode STRING_LITERAL() { return getToken(STOCLParser.STRING_LITERAL, 0); }
		public StringValueLiteralContext(OclStringValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclStringValueContext oclStringValue() throws RecognitionException {
		OclStringValueContext _localctx = new OclStringValueContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_oclStringValue);
		try {
			setState(274);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new StringValueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				match(STRING_LITERAL);
				}
				break;
			case 2:
				_localctx = new StringValueVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(269);
				var();
				}
				break;
			case 3:
				_localctx = new StringValueFAttrContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(270);
				oclObject(0);
				setState(271);
				match(T__5);
				setState(272);
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
	public static class BoolOpContext extends ParserRuleContext {
		public BoolOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitBoolOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolOpContext boolOp() throws RecognitionException {
		BoolOpContext _localctx = new BoolOpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_boolOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16492674416640L) != 0)) ) {
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
	public static class CompOpContext extends ParserRuleContext {
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitCompOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 263882793811968L) != 0)) ) {
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
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitVarListValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarListContext varList() throws RecognitionException {
		VarListContext _localctx = new VarListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_varList);
		int _la;
		try {
			_localctx = new VarListValueContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			var();
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__47) {
				{
				{
				setState(281);
				match(T__47);
				setState(282);
				var();
				}
				}
				setState(287);
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
		public List<TerminalNode> ID() { return getTokens(STOCLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STOCLParser.ID, i);
		}
		public VarIDContext(VarContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitVarID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_var);
		try {
			_localctx = new VarIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(ID);
			setState(291);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(289);
				match(T__1);
				setState(290);
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
	public static class RoleOrAttrContext extends ParserRuleContext {
		public RoleOrAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_roleOrAttr; }
	 
		public RoleOrAttrContext() { }
		public void copyFrom(RoleOrAttrContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RoleOrAttrIDContext extends RoleOrAttrContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public RoleOrAttrIDContext(RoleOrAttrContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitRoleOrAttrID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoleOrAttrContext roleOrAttr() throws RecognitionException {
		RoleOrAttrContext _localctx = new RoleOrAttrContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_roleOrAttr);
		try {
			_localctx = new RoleOrAttrIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
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
	public static class FRoleContext extends ParserRuleContext {
		public FRoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fRole; }
	 
		public FRoleContext() { }
		public void copyFrom(FRoleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FRoleIDContext extends FRoleContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public FRoleIDContext(FRoleContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitFRoleID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FRoleContext fRole() throws RecognitionException {
		FRoleContext _localctx = new FRoleContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fRole);
		try {
			_localctx = new FRoleIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
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
		public NfRoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nfRole; }
	 
		public NfRoleContext() { }
		public void copyFrom(NfRoleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NfRoleIDContext extends NfRoleContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public NfRoleIDContext(NfRoleContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitNfRoleID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NfRoleContext nfRole() throws RecognitionException {
		NfRoleContext _localctx = new NfRoleContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_nfRole);
		try {
			_localctx = new NfRoleIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
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
		public AttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attr; }
	 
		public AttrContext() { }
		public void copyFrom(AttrContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttrIDContext extends AttrContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public AttrIDContext(AttrContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitAttrID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttrContext attr() throws RecognitionException {
		AttrContext _localctx = new AttrContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_attr);
		try {
			_localctx = new AttrIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
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
	public static class BAttrContext extends ParserRuleContext {
		public BAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bAttr; }
	 
		public BAttrContext() { }
		public void copyFrom(BAttrContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BAttrIDContext extends BAttrContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public BAttrIDContext(BAttrContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitBAttrID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BAttrContext bAttr() throws RecognitionException {
		BAttrContext _localctx = new BAttrContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_bAttr);
		try {
			_localctx = new BAttrIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
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
		case 6:
			return arithmeticExpr_sempred((ArithmeticExprContext)_localctx, predIndex);
		case 7:
			return oclSet_sempred((OclSetContext)_localctx, predIndex);
		case 8:
			return oclObject_sempred((OclObjectContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean oclBool_sempred(OclBoolContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean arithmeticExpr_sempred(ArithmeticExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean oclSet_sempred(OclSetContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean oclObject_sempred(OclObjectContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001:\u0130\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u0000-\b\u0000\n\u0000\f\u00000\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0004\u00017\b\u0001\u000b\u0001"+
		"\f\u00018\u0001\u0002\u0001\u0002\u0003\u0002=\b\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003O\b\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0005\u0003U\b\u0003\n\u0003\f\u0003X\t"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u008e"+
		"\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005\u009c\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00a6"+
		"\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u0006\u00ae\b\u0006\n\u0006\f\u0006\u00b1\t\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007\u00bb\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u00e2\b\u0007"+
		"\n\u0007\f\u0007\u00e5\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b"+
		"\u00eb\b\b\u0001\b\u0001\b\u0001\b\u0005\b\u00f0\b\b\n\b\f\b\u00f3\t\b"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u010b\b\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u0113\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\r\u0005\r\u011c\b\r\n\r\f\r\u011f\t\r"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0124\b\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0000\u0004\u0006\f\u000e"+
		"\u0010\u0014\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&\u0000\u0005\u0001\u0000\u0017\u0018\u0002"+
		"\u0000\u0016\u0016\u0019\u0019\u0002\u0000\u0006\u0006  \u0001\u0000("+
		"+\u0002\u0000\u0014\u0015,/\u0149\u0000(\u0001\u0000\u0000\u0000\u0002"+
		"3\u0001\u0000\u0000\u0000\u0004:\u0001\u0000\u0000\u0000\u0006N\u0001"+
		"\u0000\u0000\u0000\b\u008d\u0001\u0000\u0000\u0000\n\u009b\u0001\u0000"+
		"\u0000\u0000\f\u00a5\u0001\u0000\u0000\u0000\u000e\u00ba\u0001\u0000\u0000"+
		"\u0000\u0010\u00ea\u0001\u0000\u0000\u0000\u0012\u010a\u0001\u0000\u0000"+
		"\u0000\u0014\u0112\u0001\u0000\u0000\u0000\u0016\u0114\u0001\u0000\u0000"+
		"\u0000\u0018\u0116\u0001\u0000\u0000\u0000\u001a\u0118\u0001\u0000\u0000"+
		"\u0000\u001c\u0120\u0001\u0000\u0000\u0000\u001e\u0125\u0001\u0000\u0000"+
		"\u0000 \u0127\u0001\u0000\u0000\u0000\"\u0129\u0001\u0000\u0000\u0000"+
		"$\u012b\u0001\u0000\u0000\u0000&\u012d\u0001\u0000\u0000\u0000()\u0005"+
		"\u0001\u0000\u0000)*\u00059\u0000\u0000*.\u0005\u0002\u0000\u0000+-\u0003"+
		"\u0002\u0001\u0000,+\u0001\u0000\u0000\u0000-0\u0001\u0000\u0000\u0000"+
		".,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/1\u0001\u0000\u0000"+
		"\u00000.\u0001\u0000\u0000\u000012\u0005\u0000\u0000\u00012\u0001\u0001"+
		"\u0000\u0000\u000034\u0005\u0003\u0000\u000046\u00059\u0000\u000057\u0003"+
		"\u0004\u0002\u000065\u0001\u0000\u0000\u000078\u0001\u0000\u0000\u0000"+
		"86\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009\u0003\u0001\u0000"+
		"\u0000\u0000:<\u0005\u0004\u0000\u0000;=\u00059\u0000\u0000<;\u0001\u0000"+
		"\u0000\u0000<=\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>?\u0005"+
		"\u0002\u0000\u0000?@\u0003\u0006\u0003\u0000@\u0005\u0001\u0000\u0000"+
		"\u0000AB\u0006\u0003\uffff\uffff\u0000BC\u0005\u0005\u0000\u0000CO\u0003"+
		"\u0006\u0003\u0006DO\u0003\n\u0005\u0000EF\u0003\u0010\b\u0000FG\u0005"+
		"\u0006\u0000\u0000GH\u0003&\u0013\u0000HO\u0001\u0000\u0000\u0000IO\u0003"+
		"\b\u0004\u0000JK\u0005\u0007\u0000\u0000KL\u0003\u0006\u0003\u0000LM\u0005"+
		"\b\u0000\u0000MO\u0001\u0000\u0000\u0000NA\u0001\u0000\u0000\u0000ND\u0001"+
		"\u0000\u0000\u0000NE\u0001\u0000\u0000\u0000NI\u0001\u0000\u0000\u0000"+
		"NJ\u0001\u0000\u0000\u0000OV\u0001\u0000\u0000\u0000PQ\n\u0005\u0000\u0000"+
		"QR\u0003\u0016\u000b\u0000RS\u0003\u0006\u0003\u0006SU\u0001\u0000\u0000"+
		"\u0000TP\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000"+
		"\u0000\u0000VW\u0001\u0000\u0000\u0000W\u0007\u0001\u0000\u0000\u0000"+
		"XV\u0001\u0000\u0000\u0000YZ\u0003\u000e\u0007\u0000Z[\u0005\t\u0000\u0000"+
		"[\\\u0003\u000e\u0007\u0000\\]\u0005\b\u0000\u0000]\u008e\u0001\u0000"+
		"\u0000\u0000^_\u0003\u000e\u0007\u0000_`\u0005\n\u0000\u0000`a\u0003\u000e"+
		"\u0007\u0000ab\u0005\b\u0000\u0000b\u008e\u0001\u0000\u0000\u0000cd\u0003"+
		"\u000e\u0007\u0000de\u0005\u000b\u0000\u0000ef\u0003\u0010\b\u0000fg\u0005"+
		"\b\u0000\u0000g\u008e\u0001\u0000\u0000\u0000hi\u0003\u000e\u0007\u0000"+
		"ij\u0005\f\u0000\u0000jk\u0003\u0010\b\u0000kl\u0005\b\u0000\u0000l\u008e"+
		"\u0001\u0000\u0000\u0000mn\u0003\u000e\u0007\u0000no\u0005\r\u0000\u0000"+
		"op\u0003\u001a\r\u0000pq\u0005\u000e\u0000\u0000qr\u0003\u0006\u0003\u0000"+
		"rs\u0005\b\u0000\u0000s\u008e\u0001\u0000\u0000\u0000tu\u0003\u000e\u0007"+
		"\u0000uv\u0005\u000f\u0000\u0000vw\u0003\u001a\r\u0000wx\u0005\u000e\u0000"+
		"\u0000xy\u0003\u0006\u0003\u0000yz\u0005\b\u0000\u0000z\u008e\u0001\u0000"+
		"\u0000\u0000{|\u0003\u000e\u0007\u0000|}\u0005\u0010\u0000\u0000}\u008e"+
		"\u0001\u0000\u0000\u0000~\u007f\u0003\u000e\u0007\u0000\u007f\u0080\u0005"+
		"\u0011\u0000\u0000\u0080\u008e\u0001\u0000\u0000\u0000\u0081\u0082\u0003"+
		"\u000e\u0007\u0000\u0082\u0083\u0005\u0012\u0000\u0000\u0083\u0084\u0003"+
		"\u001a\r\u0000\u0084\u0085\u0005\u000e\u0000\u0000\u0085\u0086\u0003\u0006"+
		"\u0003\u0000\u0086\u0087\u0005\b\u0000\u0000\u0087\u008e\u0001\u0000\u0000"+
		"\u0000\u0088\u0089\u0003\u000e\u0007\u0000\u0089\u008a\u0005\u0013\u0000"+
		"\u0000\u008a\u008b\u0003$\u0012\u0000\u008b\u008c\u0005\b\u0000\u0000"+
		"\u008c\u008e\u0001\u0000\u0000\u0000\u008dY\u0001\u0000\u0000\u0000\u008d"+
		"^\u0001\u0000\u0000\u0000\u008dc\u0001\u0000\u0000\u0000\u008dh\u0001"+
		"\u0000\u0000\u0000\u008dm\u0001\u0000\u0000\u0000\u008dt\u0001\u0000\u0000"+
		"\u0000\u008d{\u0001\u0000\u0000\u0000\u008d~\u0001\u0000\u0000\u0000\u008d"+
		"\u0081\u0001\u0000\u0000\u0000\u008d\u0088\u0001\u0000\u0000\u0000\u008e"+
		"\t\u0001\u0000\u0000\u0000\u008f\u0090\u0003\f\u0006\u0000\u0090\u0091"+
		"\u0003\u0018\f\u0000\u0091\u0092\u0003\f\u0006\u0000\u0092\u009c\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0003\u0014\n\u0000\u0094\u0095\u0005\u0014"+
		"\u0000\u0000\u0095\u0096\u0003\u0014\n\u0000\u0096\u009c\u0001\u0000\u0000"+
		"\u0000\u0097\u0098\u0003\u0014\n\u0000\u0098\u0099\u0005\u0015\u0000\u0000"+
		"\u0099\u009a\u0003\u0014\n\u0000\u009a\u009c\u0001\u0000\u0000\u0000\u009b"+
		"\u008f\u0001\u0000\u0000\u0000\u009b\u0093\u0001\u0000\u0000\u0000\u009b"+
		"\u0097\u0001\u0000\u0000\u0000\u009c\u000b\u0001\u0000\u0000\u0000\u009d"+
		"\u009e\u0006\u0006\uffff\uffff\u0000\u009e\u009f\u0005\u0016\u0000\u0000"+
		"\u009f\u00a6\u0003\f\u0006\u0005\u00a0\u00a1\u0005\u0007\u0000\u0000\u00a1"+
		"\u00a2\u0003\f\u0006\u0000\u00a2\u00a3\u0005\b\u0000\u0000\u00a3\u00a6"+
		"\u0001\u0000\u0000\u0000\u00a4\u00a6\u0003\u0012\t\u0000\u00a5\u009d\u0001"+
		"\u0000\u0000\u0000\u00a5\u00a0\u0001\u0000\u0000\u0000\u00a5\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a6\u00af\u0001\u0000\u0000\u0000\u00a7\u00a8\n\u0004"+
		"\u0000\u0000\u00a8\u00a9\u0007\u0000\u0000\u0000\u00a9\u00ae\u0003\f\u0006"+
		"\u0005\u00aa\u00ab\n\u0003\u0000\u0000\u00ab\u00ac\u0007\u0001\u0000\u0000"+
		"\u00ac\u00ae\u0003\f\u0006\u0004\u00ad\u00a7\u0001\u0000\u0000\u0000\u00ad"+
		"\u00aa\u0001\u0000\u0000\u0000\u00ae\u00b1\u0001\u0000\u0000\u0000\u00af"+
		"\u00ad\u0001\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0"+
		"\r\u0001\u0000\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b2\u00b3"+
		"\u0006\u0007\uffff\uffff\u0000\u00b3\u00b4\u0003\u0010\b\u0000\u00b4\u00b5"+
		"\u0005\u0006\u0000\u0000\u00b5\u00b6\u0003\"\u0011\u0000\u00b6\u00bb\u0001"+
		"\u0000\u0000\u0000\u00b7\u00b8\u00059\u0000\u0000\u00b8\u00b9\u0007\u0002"+
		"\u0000\u0000\u00b9\u00bb\u0005!\u0000\u0000\u00ba\u00b2\u0001\u0000\u0000"+
		"\u0000\u00ba\u00b7\u0001\u0000\u0000\u0000\u00bb\u00e3\u0001\u0000\u0000"+
		"\u0000\u00bc\u00bd\n\t\u0000\u0000\u00bd\u00be\u0005\u001a\u0000\u0000"+
		"\u00be\u00bf\u0003\u000e\u0007\u0000\u00bf\u00c0\u0005\b\u0000\u0000\u00c0"+
		"\u00e2\u0001\u0000\u0000\u0000\u00c1\u00c2\n\b\u0000\u0000\u00c2\u00c3"+
		"\u0005\u001b\u0000\u0000\u00c3\u00c4\u0003\u000e\u0007\u0000\u00c4\u00c5"+
		"\u0005\b\u0000\u0000\u00c5\u00e2\u0001\u0000\u0000\u0000\u00c6\u00c7\n"+
		"\u0007\u0000\u0000\u00c7\u00c8\u0005\u001c\u0000\u0000\u00c8\u00c9\u0003"+
		"\u000e\u0007\u0000\u00c9\u00ca\u0005\b\u0000\u0000\u00ca\u00e2\u0001\u0000"+
		"\u0000\u0000\u00cb\u00cc\n\u0006\u0000\u0000\u00cc\u00cd\u0005\u001d\u0000"+
		"\u0000\u00cd\u00ce\u0003\u000e\u0007\u0000\u00ce\u00cf\u0005\b\u0000\u0000"+
		"\u00cf\u00e2\u0001\u0000\u0000\u0000\u00d0\u00d1\n\u0005\u0000\u0000\u00d1"+
		"\u00d2\u0005\u001e\u0000\u0000\u00d2\u00d3\u0003\u001a\r\u0000\u00d3\u00d4"+
		"\u0005\u000e\u0000\u0000\u00d4\u00d5\u0003\u0006\u0003\u0000\u00d5\u00d6"+
		"\u0005\b\u0000\u0000\u00d6\u00e2\u0001\u0000\u0000\u0000\u00d7\u00d8\n"+
		"\u0004\u0000\u0000\u00d8\u00d9\u0005\u001f\u0000\u0000\u00d9\u00da\u0003"+
		"\u001a\r\u0000\u00da\u00db\u0005\u000e\u0000\u0000\u00db\u00dc\u0003\u0006"+
		"\u0003\u0000\u00dc\u00dd\u0005\b\u0000\u0000\u00dd\u00e2\u0001\u0000\u0000"+
		"\u0000\u00de\u00df\n\u0003\u0000\u0000\u00df\u00e0\u0005\u0006\u0000\u0000"+
		"\u00e0\u00e2\u0003\u001e\u000f\u0000\u00e1\u00bc\u0001\u0000\u0000\u0000"+
		"\u00e1\u00c1\u0001\u0000\u0000\u0000\u00e1\u00c6\u0001\u0000\u0000\u0000"+
		"\u00e1\u00cb\u0001\u0000\u0000\u0000\u00e1\u00d0\u0001\u0000\u0000\u0000"+
		"\u00e1\u00d7\u0001\u0000\u0000\u0000\u00e1\u00de\u0001\u0000\u0000\u0000"+
		"\u00e2\u00e5\u0001\u0000\u0000\u0000\u00e3\u00e1\u0001\u0000\u0000\u0000"+
		"\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4\u000f\u0001\u0000\u0000\u0000"+
		"\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e6\u00e7\u0006\b\uffff\uffff\u0000"+
		"\u00e7\u00eb\u0003\u001c\u000e\u0000\u00e8\u00eb\u0005\"\u0000\u0000\u00e9"+
		"\u00eb\u00053\u0000\u0000\u00ea\u00e6\u0001\u0000\u0000\u0000\u00ea\u00e8"+
		"\u0001\u0000\u0000\u0000\u00ea\u00e9\u0001\u0000\u0000\u0000\u00eb\u00f1"+
		"\u0001\u0000\u0000\u0000\u00ec\u00ed\n\u0004\u0000\u0000\u00ed\u00ee\u0005"+
		"\u0006\u0000\u0000\u00ee\u00f0\u0003 \u0010\u0000\u00ef\u00ec\u0001\u0000"+
		"\u0000\u0000\u00f0\u00f3\u0001\u0000\u0000\u0000\u00f1\u00ef\u0001\u0000"+
		"\u0000\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u0011\u0001\u0000"+
		"\u0000\u0000\u00f3\u00f1\u0001\u0000\u0000\u0000\u00f4\u010b\u00051\u0000"+
		"\u0000\u00f5\u010b\u00052\u0000\u0000\u00f6\u010b\u0003\u001c\u000e\u0000"+
		"\u00f7\u00f8\u0003\u0010\b\u0000\u00f8\u00f9\u0005\u0006\u0000\u0000\u00f9"+
		"\u00fa\u0003$\u0012\u0000\u00fa\u010b\u0001\u0000\u0000\u0000\u00fb\u00fc"+
		"\u0003\u000e\u0007\u0000\u00fc\u00fd\u0005#\u0000\u0000\u00fd\u010b\u0001"+
		"\u0000\u0000\u0000\u00fe\u00ff\u0003\u000e\u0007\u0000\u00ff\u0100\u0005"+
		"$\u0000\u0000\u0100\u010b\u0001\u0000\u0000\u0000\u0101\u0102\u0003\u000e"+
		"\u0007\u0000\u0102\u0103\u0005%\u0000\u0000\u0103\u010b\u0001\u0000\u0000"+
		"\u0000\u0104\u0105\u0003\u000e\u0007\u0000\u0105\u0106\u0005&\u0000\u0000"+
		"\u0106\u010b\u0001\u0000\u0000\u0000\u0107\u0108\u0003\u000e\u0007\u0000"+
		"\u0108\u0109\u0005\'\u0000\u0000\u0109\u010b\u0001\u0000\u0000\u0000\u010a"+
		"\u00f4\u0001\u0000\u0000\u0000\u010a\u00f5\u0001\u0000\u0000\u0000\u010a"+
		"\u00f6\u0001\u0000\u0000\u0000\u010a\u00f7\u0001\u0000\u0000\u0000\u010a"+
		"\u00fb\u0001\u0000\u0000\u0000\u010a\u00fe\u0001\u0000\u0000\u0000\u010a"+
		"\u0101\u0001\u0000\u0000\u0000\u010a\u0104\u0001\u0000\u0000\u0000\u010a"+
		"\u0107\u0001\u0000\u0000\u0000\u010b\u0013\u0001\u0000\u0000\u0000\u010c"+
		"\u0113\u00053\u0000\u0000\u010d\u0113\u0003\u001c\u000e\u0000\u010e\u010f"+
		"\u0003\u0010\b\u0000\u010f\u0110\u0005\u0006\u0000\u0000\u0110\u0111\u0003"+
		"$\u0012\u0000\u0111\u0113\u0001\u0000\u0000\u0000\u0112\u010c\u0001\u0000"+
		"\u0000\u0000\u0112\u010d\u0001\u0000\u0000\u0000\u0112\u010e\u0001\u0000"+
		"\u0000\u0000\u0113\u0015\u0001\u0000\u0000\u0000\u0114\u0115\u0007\u0003"+
		"\u0000\u0000\u0115\u0017\u0001\u0000\u0000\u0000\u0116\u0117\u0007\u0004"+
		"\u0000\u0000\u0117\u0019\u0001\u0000\u0000\u0000\u0118\u011d\u0003\u001c"+
		"\u000e\u0000\u0119\u011a\u00050\u0000\u0000\u011a\u011c\u0003\u001c\u000e"+
		"\u0000\u011b\u0119\u0001\u0000\u0000\u0000\u011c\u011f\u0001\u0000\u0000"+
		"\u0000\u011d\u011b\u0001\u0000\u0000\u0000\u011d\u011e\u0001\u0000\u0000"+
		"\u0000\u011e\u001b\u0001\u0000\u0000\u0000\u011f\u011d\u0001\u0000\u0000"+
		"\u0000\u0120\u0123\u00059\u0000\u0000\u0121\u0122\u0005\u0002\u0000\u0000"+
		"\u0122\u0124\u00059\u0000\u0000\u0123\u0121\u0001\u0000\u0000\u0000\u0123"+
		"\u0124\u0001\u0000\u0000\u0000\u0124\u001d\u0001\u0000\u0000\u0000\u0125"+
		"\u0126\u00059\u0000\u0000\u0126\u001f\u0001\u0000\u0000\u0000\u0127\u0128"+
		"\u00059\u0000\u0000\u0128!\u0001\u0000\u0000\u0000\u0129\u012a\u00059"+
		"\u0000\u0000\u012a#\u0001\u0000\u0000\u0000\u012b\u012c\u00059\u0000\u0000"+
		"\u012c%\u0001\u0000\u0000\u0000\u012d\u012e\u00059\u0000\u0000\u012e\'"+
		"\u0001\u0000\u0000\u0000\u0013.8<NV\u008d\u009b\u00a5\u00ad\u00af\u00ba"+
		"\u00e1\u00e3\u00ea\u00f1\u010a\u0112\u011d\u0123";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}