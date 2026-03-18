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
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, POINT=92, LINESTRING=93, POLYGON=94, 
		TIMESTAMP_LITERAL=95, INT_LITERAL=96, REAL_LITERAL=97, STRING_LITERAL=98, 
		BOOLEAN_LITERAL=99, NEWLINE=100, LINE_COMMENT=101, PARA_COMMENT=102, WS=103, 
		SPACE=104, ID=105, CONSTANTID=106;
	public static final int
		RULE_oclBool = 0, RULE_equalExpr = 1, RULE_arithExpr = 2, RULE_spatialPredicate = 3, 
		RULE_geom = 4, RULE_periodPredicate = 5, RULE_period = 6, RULE_timestamp = 7, 
		RULE_specification = 8, RULE_context = 9, RULE_inv = 10, RULE_bagPredicate = 11, 
		RULE_oclBag = 12, RULE_oclObj = 13, RULE_objAttrValue = 14, RULE_strValue = 15, 
		RULE_literal = 16, RULE_varList = 17, RULE_var = 18, RULE_roleOrAttr = 19, 
		RULE_role = 20, RULE_attr = 21, RULE_bAttr = 22;
	private static String[] makeRuleNames() {
		return new String[] {
			"oclBool", "equalExpr", "arithExpr", "spatialPredicate", "geom", "periodPredicate", 
			"period", "timestamp", "specification", "context", "inv", "bagPredicate", 
			"oclBag", "oclObj", "objAttrValue", "strValue", "literal", "varList", 
			"var", "roleOrAttr", "role", "attr", "bAttr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'not'", "'and'", "'or'", "'implies'", "'xor'", "'.'", "'('", "')'", 
			"'<'", "'<='", "'='", "'>='", "'>'", "'<>'", "'-'", "'*'", "'/'", "'+'", 
			"'->min()'", "'->max()'", "'->size()'", "'->sum()'", "'->avg()'", "'.size()'", 
			"'.abs()'", "'.floor()'", "'.round()'", "'.max('", "'.min('", "'.mod('", 
			"'.div('", "'.distance('", "'.contains('", "'.containsProperly('", "'.coveredBy('", 
			"'.covers('", "'.crosses('", "'.disjoint('", "'.equals('", "'.intersects('", 
			"'.overlaps('", "'.touches('", "'.within('", "'.dWithin('", "','", "'.relate('", 
			"'.buffer('", "'.union('", "'.intersection('", "'.difference('", "'.symDifference('", 
			"'.convexHull()'", "'.centroid()'", "'.envelope()'", "'.precedes('", 
			"'.immediatelyPrecedes('", "'.succeeds('", "'.immediatelySucceeds('", 
			"'['", "']'", "'Model'", "':'", "'context'", "'inv'", "'->includesAll('", 
			"'->excludesAll('", "'->includes('", "'->excludes('", "'->isEmpty()'", 
			"'->notEmpty()'", "'->forAll('", "'|'", "'->exists('", "'->one('", "'->isUnique('", 
			"'->union('", "'->intersection('", "'->difference('", "'->symmetricDifference('", 
			"'->select('", "'->reject('", "'->collect('", "'allInstances()'", "'Bag {'", 
			"'}'", "'self'", "'.concat('", "'.substring('", "'.toUpperCase()'", "'.toLowerCase()'", 
			"'.at('", null, null, null, null, null, null, null, null, null, null, 
			null, null, "' '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "POINT", "LINESTRING", 
			"POLYGON", "TIMESTAMP_LITERAL", "INT_LITERAL", "REAL_LITERAL", "STRING_LITERAL", 
			"BOOLEAN_LITERAL", "NEWLINE", "LINE_COMMENT", "PARA_COMMENT", "WS", "SPACE", 
			"ID", "CONSTANTID"
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
	public static class OclBoolBagPredicateContext extends OclBoolContext {
		public BagPredicateContext bagPredicate() {
			return getRuleContext(BagPredicateContext.class,0);
		}
		public OclBoolBagPredicateContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolBagPredicate(this);
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
	public static class OclBoolAndOrContext extends OclBoolContext {
		public Token boolOp;
		public List<OclBoolContext> oclBool() {
			return getRuleContexts(OclBoolContext.class);
		}
		public OclBoolContext oclBool(int i) {
			return getRuleContext(OclBoolContext.class,i);
		}
		public OclBoolAndOrContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolAndOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolEqualityExprContext extends OclBoolContext {
		public EqualExprContext equalExpr() {
			return getRuleContext(EqualExprContext.class,0);
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
		public OclObjContext oclObj() {
			return getRuleContext(OclObjContext.class,0);
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
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolSpatialPredicateContext extends OclBoolContext {
		public SpatialPredicateContext spatialPredicate() {
			return getRuleContext(SpatialPredicateContext.class,0);
		}
		public OclBoolSpatialPredicateContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolSpatialPredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolImpliesXorContext extends OclBoolContext {
		public Token boolOp;
		public List<OclBoolContext> oclBool() {
			return getRuleContexts(OclBoolContext.class);
		}
		public OclBoolContext oclBool(int i) {
			return getRuleContext(OclBoolContext.class,i);
		}
		public OclBoolImpliesXorContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolImpliesXor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclBoolPeriodPredicateContext extends OclBoolContext {
		public PeriodPredicateContext periodPredicate() {
			return getRuleContext(PeriodPredicateContext.class,0);
		}
		public OclBoolPeriodPredicateContext(OclBoolContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclBoolPeriodPredicate(this);
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
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_oclBool, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				_localctx = new OclBoolNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(47);
				match(T__0);
				setState(48);
				oclBool(9);
				}
				break;
			case 2:
				{
				_localctx = new OclBoolEqualityExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(49);
				equalExpr();
				}
				break;
			case 3:
				{
				_localctx = new OclBoolBAttrContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(50);
				oclObj(0);
				setState(51);
				match(T__5);
				setState(52);
				bAttr();
				}
				break;
			case 4:
				{
				_localctx = new OclBoolBagPredicateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54);
				bagPredicate();
				}
				break;
			case 5:
				{
				_localctx = new OclBoolParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55);
				match(T__6);
				setState(56);
				oclBool(0);
				setState(57);
				match(T__7);
				}
				break;
			case 6:
				{
				_localctx = new OclBoolSpatialPredicateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(59);
				spatialPredicate();
				}
				break;
			case 7:
				{
				_localctx = new OclBoolPeriodPredicateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(60);
				periodPredicate();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(71);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(69);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new OclBoolAndOrContext(new OclBoolContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBool);
						setState(63);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(64);
						((OclBoolAndOrContext)_localctx).boolOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__1 || _la==T__2) ) {
							((OclBoolAndOrContext)_localctx).boolOp = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(65);
						oclBool(9);
						}
						break;
					case 2:
						{
						_localctx = new OclBoolImpliesXorContext(new OclBoolContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBool);
						setState(66);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(67);
						((OclBoolImpliesXorContext)_localctx).boolOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__3 || _la==T__4) ) {
							((OclBoolImpliesXorContext)_localctx).boolOp = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(68);
						oclBool(8);
						}
						break;
					}
					} 
				}
				setState(73);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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
	public static class EqualExprContext extends ParserRuleContext {
		public EqualExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalExpr; }
	 
		public EqualExprContext() { }
		public void copyFrom(EqualExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprObjectContext extends EqualExprContext {
		public Token compOp;
		public List<OclObjContext> oclObj() {
			return getRuleContexts(OclObjContext.class);
		}
		public OclObjContext oclObj(int i) {
			return getRuleContext(OclObjContext.class,i);
		}
		public EqualityExprObjectContext(EqualExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprObject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprStringContext extends EqualExprContext {
		public Token compOp;
		public List<StrValueContext> strValue() {
			return getRuleContexts(StrValueContext.class);
		}
		public StrValueContext strValue(int i) {
			return getRuleContext(StrValueContext.class,i);
		}
		public EqualityExprStringContext(EqualExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprTimestampContext extends EqualExprContext {
		public Token compOp;
		public List<TimestampContext> timestamp() {
			return getRuleContexts(TimestampContext.class);
		}
		public TimestampContext timestamp(int i) {
			return getRuleContext(TimestampContext.class,i);
		}
		public EqualityExprTimestampContext(EqualExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprTimestamp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprObjAttrValueContext extends EqualExprContext {
		public Token compOp;
		public List<ObjAttrValueContext> objAttrValue() {
			return getRuleContexts(ObjAttrValueContext.class);
		}
		public ObjAttrValueContext objAttrValue(int i) {
			return getRuleContext(ObjAttrValueContext.class,i);
		}
		public EqualityExprObjAttrValueContext(EqualExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprObjAttrValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprArithmeticContext extends EqualExprContext {
		public Token compOp;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public EqualityExprArithmeticContext(EqualExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitEqualityExprArithmetic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualExprContext equalExpr() throws RecognitionException {
		EqualExprContext _localctx = new EqualExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_equalExpr);
		int _la;
		try {
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new EqualityExprObjAttrValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				objAttrValue();
				setState(75);
				((EqualityExprObjAttrValueContext)_localctx).compOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 32256L) != 0)) ) {
					((EqualityExprObjAttrValueContext)_localctx).compOp = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(76);
				objAttrValue();
				}
				break;
			case 2:
				_localctx = new EqualityExprArithmeticContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				arithExpr(0);
				setState(79);
				((EqualityExprArithmeticContext)_localctx).compOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 32256L) != 0)) ) {
					((EqualityExprArithmeticContext)_localctx).compOp = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(80);
				arithExpr(0);
				}
				break;
			case 3:
				_localctx = new EqualityExprStringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				strValue(0);
				setState(83);
				((EqualityExprStringContext)_localctx).compOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__10 || _la==T__13) ) {
					((EqualityExprStringContext)_localctx).compOp = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(84);
				strValue(0);
				}
				break;
			case 4:
				_localctx = new EqualityExprObjectContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				oclObj(0);
				setState(87);
				((EqualityExprObjectContext)_localctx).compOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__10 || _la==T__13) ) {
					((EqualityExprObjectContext)_localctx).compOp = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(88);
				oclObj(0);
				}
				break;
			case 5:
				_localctx = new EqualityExprTimestampContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(90);
				timestamp();
				setState(91);
				((EqualityExprTimestampContext)_localctx).compOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 32256L) != 0)) ) {
					((EqualityExprTimestampContext)_localctx).compOp = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(92);
				timestamp();
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
	public static class ArithExprContext extends ParserRuleContext {
		public ArithExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithExpr; }
	 
		public ArithExprContext() { }
		public void copyFrom(ArithExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueAggFuncContext extends ArithExprContext {
		public Token aggFunc;
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public ArithValueAggFuncContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueAggFunc(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueDivContext extends ArithExprContext {
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public ArithValueDivContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueAbsContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ArithValueAbsContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueAbs(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueMinContext extends ArithExprContext {
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public ArithValueMinContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueMin(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithParenContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ArithParenContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithParen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithUnaryMinusContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ArithUnaryMinusContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithUnaryMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithMultDivContext extends ArithExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public ArithMultDivContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithMultDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithAddSubContext extends ArithExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public ArithAddSubContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueRoundContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ArithValueRoundContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueRound(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryDistanceContext extends ArithExprContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public GeometryDistanceContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryDistance(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueStrSizeContext extends ArithExprContext {
		public StrValueContext strValue() {
			return getRuleContext(StrValueContext.class,0);
		}
		public ArithValueStrSizeContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueStrSize(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueFloorContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ArithValueFloorContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueFloor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueMaxContext extends ArithExprContext {
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public ArithValueMaxContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueMax(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueModContext extends ArithExprContext {
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public ArithValueModContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueMod(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueIntLiteralContext extends ArithExprContext {
		public TerminalNode INT_LITERAL() { return getToken(STOCLParser.INT_LITERAL, 0); }
		public ArithValueIntLiteralContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueRealLiteralContext extends ArithExprContext {
		public TerminalNode REAL_LITERAL() { return getToken(STOCLParser.REAL_LITERAL, 0); }
		public ArithValueRealLiteralContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueRealLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithValueObjAttrValueContext extends ArithExprContext {
		public ObjAttrValueContext objAttrValue() {
			return getRuleContext(ObjAttrValueContext.class,0);
		}
		public ArithValueObjAttrValueContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitArithValueObjAttrValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithExprContext arithExpr() throws RecognitionException {
		return arithExpr(0);
	}

	private ArithExprContext arithExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArithExprContext _localctx = new ArithExprContext(_ctx, _parentState);
		ArithExprContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_arithExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				_localctx = new ArithUnaryMinusContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(97);
				match(T__14);
				setState(98);
				arithExpr(17);
				}
				break;
			case 2:
				{
				_localctx = new ArithParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(99);
				match(T__6);
				setState(100);
				arithExpr(0);
				setState(101);
				match(T__7);
				}
				break;
			case 3:
				{
				_localctx = new ArithValueIntLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(103);
				match(INT_LITERAL);
				}
				break;
			case 4:
				{
				_localctx = new ArithValueRealLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(104);
				match(REAL_LITERAL);
				}
				break;
			case 5:
				{
				_localctx = new ArithValueObjAttrValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(105);
				objAttrValue();
				}
				break;
			case 6:
				{
				_localctx = new ArithValueAggFuncContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(106);
				oclBag(0);
				setState(107);
				((ArithValueAggFuncContext)_localctx).aggFunc = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16252928L) != 0)) ) {
					((ArithValueAggFuncContext)_localctx).aggFunc = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 7:
				{
				_localctx = new ArithValueStrSizeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(109);
				strValue(0);
				setState(110);
				match(T__23);
				}
				break;
			case 8:
				{
				_localctx = new GeometryDistanceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(112);
				geom(0);
				setState(113);
				match(T__31);
				setState(114);
				geom(0);
				setState(115);
				match(T__7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(153);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(151);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new ArithMultDivContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(119);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(120);
						((ArithMultDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__15 || _la==T__16) ) {
							((ArithMultDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(121);
						arithExpr(17);
						}
						break;
					case 2:
						{
						_localctx = new ArithAddSubContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(122);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(123);
						((ArithAddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__14 || _la==T__17) ) {
							((ArithAddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(124);
						arithExpr(16);
						}
						break;
					case 3:
						{
						_localctx = new ArithValueAbsContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(125);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(126);
						match(T__24);
						}
						break;
					case 4:
						{
						_localctx = new ArithValueFloorContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(127);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(128);
						match(T__25);
						}
						break;
					case 5:
						{
						_localctx = new ArithValueRoundContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(129);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(130);
						match(T__26);
						}
						break;
					case 6:
						{
						_localctx = new ArithValueMaxContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(131);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(132);
						match(T__27);
						setState(133);
						arithExpr(0);
						setState(134);
						match(T__7);
						}
						break;
					case 7:
						{
						_localctx = new ArithValueMinContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(136);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(137);
						match(T__28);
						setState(138);
						arithExpr(0);
						setState(139);
						match(T__7);
						}
						break;
					case 8:
						{
						_localctx = new ArithValueModContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(141);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(142);
						match(T__29);
						setState(143);
						arithExpr(0);
						setState(144);
						match(T__7);
						}
						break;
					case 9:
						{
						_localctx = new ArithValueDivContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(146);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(147);
						match(T__30);
						setState(148);
						arithExpr(0);
						setState(149);
						match(T__7);
						}
						break;
					}
					} 
				}
				setState(155);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
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
	public static class SpatialPredicateContext extends ParserRuleContext {
		public SpatialPredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_spatialPredicate; }
	 
		public SpatialPredicateContext() { }
		public void copyFrom(SpatialPredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STDWithinContext extends SpatialPredicateContext {
		public Token num;
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public TerminalNode INT_LITERAL() { return getToken(STOCLParser.INT_LITERAL, 0); }
		public TerminalNode REAL_LITERAL() { return getToken(STOCLParser.REAL_LITERAL, 0); }
		public STDWithinContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTDWithin(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STCrossesContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STCrossesContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTCrosses(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STCoversContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STCoversContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTCovers(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STContainsContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STContainsContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTContains(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STContainsProperlyContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STContainsProperlyContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTContainsProperly(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STTouchesContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STTouchesContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTTouches(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STWithinContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STWithinContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTWithin(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STRelateWithGivenIMatrixContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public TerminalNode STRING_LITERAL() { return getToken(STOCLParser.STRING_LITERAL, 0); }
		public STRelateWithGivenIMatrixContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTRelateWithGivenIMatrix(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STIntersectsContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STIntersectsContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTIntersects(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STDisjointContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STDisjointContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTDisjoint(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STCoveredByContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STCoveredByContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTCoveredBy(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STEqualsContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STEqualsContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTEquals(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STOverlapsContext extends SpatialPredicateContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public STOverlapsContext(SpatialPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSTOverlaps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpatialPredicateContext spatialPredicate() throws RecognitionException {
		SpatialPredicateContext _localctx = new SpatialPredicateContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_spatialPredicate);
		int _la;
		try {
			setState(225);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new STContainsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(156);
				geom(0);
				setState(157);
				match(T__32);
				setState(158);
				geom(0);
				setState(159);
				match(T__7);
				}
				break;
			case 2:
				_localctx = new STContainsProperlyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(161);
				geom(0);
				setState(162);
				match(T__33);
				setState(163);
				geom(0);
				setState(164);
				match(T__7);
				}
				break;
			case 3:
				_localctx = new STCoveredByContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(166);
				geom(0);
				setState(167);
				match(T__34);
				setState(168);
				geom(0);
				setState(169);
				match(T__7);
				}
				break;
			case 4:
				_localctx = new STCoversContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(171);
				geom(0);
				setState(172);
				match(T__35);
				setState(173);
				geom(0);
				setState(174);
				match(T__7);
				}
				break;
			case 5:
				_localctx = new STCrossesContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(176);
				geom(0);
				setState(177);
				match(T__36);
				setState(178);
				geom(0);
				setState(179);
				match(T__7);
				}
				break;
			case 6:
				_localctx = new STDisjointContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(181);
				geom(0);
				setState(182);
				match(T__37);
				setState(183);
				geom(0);
				setState(184);
				match(T__7);
				}
				break;
			case 7:
				_localctx = new STEqualsContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(186);
				geom(0);
				setState(187);
				match(T__38);
				setState(188);
				geom(0);
				setState(189);
				match(T__7);
				}
				break;
			case 8:
				_localctx = new STIntersectsContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(191);
				geom(0);
				setState(192);
				match(T__39);
				setState(193);
				geom(0);
				setState(194);
				match(T__7);
				}
				break;
			case 9:
				_localctx = new STOverlapsContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(196);
				geom(0);
				setState(197);
				match(T__40);
				setState(198);
				geom(0);
				setState(199);
				match(T__7);
				}
				break;
			case 10:
				_localctx = new STTouchesContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(201);
				geom(0);
				setState(202);
				match(T__41);
				setState(203);
				geom(0);
				setState(204);
				match(T__7);
				}
				break;
			case 11:
				_localctx = new STWithinContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(206);
				geom(0);
				setState(207);
				match(T__42);
				setState(208);
				geom(0);
				setState(209);
				match(T__7);
				}
				break;
			case 12:
				_localctx = new STDWithinContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(211);
				geom(0);
				setState(212);
				match(T__43);
				setState(213);
				geom(0);
				setState(214);
				match(T__44);
				setState(215);
				((STDWithinContext)_localctx).num = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==INT_LITERAL || _la==REAL_LITERAL) ) {
					((STDWithinContext)_localctx).num = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(216);
				match(T__7);
				}
				break;
			case 13:
				_localctx = new STRelateWithGivenIMatrixContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(218);
				geom(0);
				setState(219);
				match(T__45);
				setState(220);
				geom(0);
				setState(221);
				match(T__44);
				setState(222);
				match(STRING_LITERAL);
				setState(223);
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
	public static class GeomContext extends ParserRuleContext {
		public GeomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_geom; }
	 
		public GeomContext() { }
		public void copyFrom(GeomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryDifferenceContext extends GeomContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public GeometryDifferenceContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometrySymDifferenceContext extends GeomContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public GeometrySymDifferenceContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometrySymDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryLinestringContext extends GeomContext {
		public TerminalNode LINESTRING() { return getToken(STOCLParser.LINESTRING, 0); }
		public GeometryLinestringContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryLinestring(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryConvexHullContext extends GeomContext {
		public GeomContext geom() {
			return getRuleContext(GeomContext.class,0);
		}
		public GeometryConvexHullContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryConvexHull(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryPolygonContext extends GeomContext {
		public TerminalNode POLYGON() { return getToken(STOCLParser.POLYGON, 0); }
		public GeometryPolygonContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryPolygon(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryValueObjAttrValueContext extends GeomContext {
		public ObjAttrValueContext objAttrValue() {
			return getRuleContext(ObjAttrValueContext.class,0);
		}
		public GeometryValueObjAttrValueContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryValueObjAttrValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryIntersectionContext extends GeomContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public GeometryIntersectionContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryIntersection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryEnvelopeContext extends GeomContext {
		public GeomContext geom() {
			return getRuleContext(GeomContext.class,0);
		}
		public GeometryEnvelopeContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryEnvelope(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryUnionContext extends GeomContext {
		public List<GeomContext> geom() {
			return getRuleContexts(GeomContext.class);
		}
		public GeomContext geom(int i) {
			return getRuleContext(GeomContext.class,i);
		}
		public GeometryUnionContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryUnion(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryBufferContext extends GeomContext {
		public Token num;
		public GeomContext geom() {
			return getRuleContext(GeomContext.class,0);
		}
		public TerminalNode INT_LITERAL() { return getToken(STOCLParser.INT_LITERAL, 0); }
		public TerminalNode REAL_LITERAL() { return getToken(STOCLParser.REAL_LITERAL, 0); }
		public GeometryBufferContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryBuffer(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryCentroidContext extends GeomContext {
		public GeomContext geom() {
			return getRuleContext(GeomContext.class,0);
		}
		public GeometryCentroidContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryCentroid(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeometryPointContext extends GeomContext {
		public TerminalNode POINT() { return getToken(STOCLParser.POINT, 0); }
		public GeometryPointContext(GeomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitGeometryPoint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeomContext geom() throws RecognitionException {
		return geom(0);
	}

	private GeomContext geom(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		GeomContext _localctx = new GeomContext(_ctx, _parentState);
		GeomContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_geom, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case POINT:
				{
				_localctx = new GeometryPointContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(228);
				match(POINT);
				}
				break;
			case LINESTRING:
				{
				_localctx = new GeometryLinestringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(229);
				match(LINESTRING);
				}
				break;
			case POLYGON:
				{
				_localctx = new GeometryPolygonContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(230);
				match(POLYGON);
				}
				break;
			case T__85:
			case ID:
				{
				_localctx = new GeometryValueObjAttrValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(231);
				objAttrValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(266);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(264);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new GeometryBufferContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(234);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(235);
						match(T__46);
						setState(236);
						((GeometryBufferContext)_localctx).num = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==INT_LITERAL || _la==REAL_LITERAL) ) {
							((GeometryBufferContext)_localctx).num = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(237);
						match(T__7);
						}
						break;
					case 2:
						{
						_localctx = new GeometryUnionContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(238);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(239);
						match(T__47);
						setState(240);
						geom(0);
						setState(241);
						match(T__7);
						}
						break;
					case 3:
						{
						_localctx = new GeometryIntersectionContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(243);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(244);
						match(T__48);
						setState(245);
						geom(0);
						setState(246);
						match(T__7);
						}
						break;
					case 4:
						{
						_localctx = new GeometryDifferenceContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(248);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(249);
						match(T__49);
						setState(250);
						geom(0);
						setState(251);
						match(T__7);
						}
						break;
					case 5:
						{
						_localctx = new GeometrySymDifferenceContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(253);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(254);
						match(T__50);
						setState(255);
						geom(0);
						setState(256);
						match(T__7);
						}
						break;
					case 6:
						{
						_localctx = new GeometryConvexHullContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(258);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(259);
						match(T__51);
						}
						break;
					case 7:
						{
						_localctx = new GeometryCentroidContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(260);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(261);
						match(T__52);
						}
						break;
					case 8:
						{
						_localctx = new GeometryEnvelopeContext(new GeomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_geom);
						setState(262);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(263);
						match(T__53);
						}
						break;
					}
					} 
				}
				setState(268);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
	public static class PeriodPredicateContext extends ParserRuleContext {
		public PeriodPredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodPredicate; }
	 
		public PeriodPredicateContext() { }
		public void copyFrom(PeriodPredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodContainsPeriodContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodContainsPeriodContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodContainsPeriod(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodImmediatelySucceedsContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodImmediatelySucceedsContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodImmediatelySucceeds(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodEqualsContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodEqualsContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodEquals(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodPrecedesContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodPrecedesContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodPrecedes(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodOverlapsContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodOverlapsContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodOverlaps(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodImmediatelyPrecedesContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodImmediatelyPrecedesContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodImmediatelyPrecedes(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodSucceedsContext extends PeriodPredicateContext {
		public List<PeriodContext> period() {
			return getRuleContexts(PeriodContext.class);
		}
		public PeriodContext period(int i) {
			return getRuleContext(PeriodContext.class,i);
		}
		public PeriodSucceedsContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodSucceeds(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodContainsTimestampContext extends PeriodPredicateContext {
		public PeriodContext period() {
			return getRuleContext(PeriodContext.class,0);
		}
		public TimestampContext timestamp() {
			return getRuleContext(TimestampContext.class,0);
		}
		public PeriodContainsTimestampContext(PeriodPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodContainsTimestamp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodPredicateContext periodPredicate() throws RecognitionException {
		PeriodPredicateContext _localctx = new PeriodPredicateContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_periodPredicate);
		try {
			setState(309);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new PeriodContainsTimestampContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(269);
				period();
				setState(270);
				match(T__32);
				setState(271);
				timestamp();
				setState(272);
				match(T__7);
				}
				break;
			case 2:
				_localctx = new PeriodContainsPeriodContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				period();
				setState(275);
				match(T__32);
				setState(276);
				period();
				setState(277);
				match(T__7);
				}
				break;
			case 3:
				_localctx = new PeriodOverlapsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(279);
				period();
				setState(280);
				match(T__40);
				setState(281);
				period();
				setState(282);
				match(T__7);
				}
				break;
			case 4:
				_localctx = new PeriodEqualsContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(284);
				period();
				setState(285);
				match(T__38);
				setState(286);
				period();
				setState(287);
				match(T__7);
				}
				break;
			case 5:
				_localctx = new PeriodPrecedesContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(289);
				period();
				setState(290);
				match(T__54);
				setState(291);
				period();
				setState(292);
				match(T__7);
				}
				break;
			case 6:
				_localctx = new PeriodImmediatelyPrecedesContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(294);
				period();
				setState(295);
				match(T__55);
				setState(296);
				period();
				setState(297);
				match(T__7);
				}
				break;
			case 7:
				_localctx = new PeriodSucceedsContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(299);
				period();
				setState(300);
				match(T__56);
				setState(301);
				period();
				setState(302);
				match(T__7);
				}
				break;
			case 8:
				_localctx = new PeriodImmediatelySucceedsContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(304);
				period();
				setState(305);
				match(T__57);
				setState(306);
				period();
				setState(307);
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
	public static class PeriodContext extends ParserRuleContext {
		public PeriodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_period; }
	 
		public PeriodContext() { }
		public void copyFrom(PeriodContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PeriodTimestampContext extends PeriodContext {
		public Token lp;
		public Token rp;
		public List<TimestampContext> timestamp() {
			return getRuleContexts(TimestampContext.class);
		}
		public TimestampContext timestamp(int i) {
			return getRuleContext(TimestampContext.class,i);
		}
		public PeriodTimestampContext(PeriodContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitPeriodTimestamp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodContext period() throws RecognitionException {
		PeriodContext _localctx = new PeriodContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_period);
		int _la;
		try {
			_localctx = new PeriodTimestampContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			((PeriodTimestampContext)_localctx).lp = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__58) ) {
				((PeriodTimestampContext)_localctx).lp = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(312);
			timestamp();
			setState(313);
			match(T__44);
			setState(314);
			timestamp();
			setState(315);
			((PeriodTimestampContext)_localctx).rp = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__7 || _la==T__59) ) {
				((PeriodTimestampContext)_localctx).rp = (Token)_errHandler.recoverInline(this);
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
	public static class TimestampContext extends ParserRuleContext {
		public TimestampContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timestamp; }
	 
		public TimestampContext() { }
		public void copyFrom(TimestampContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TimestampValueObjAttrValueContext extends TimestampContext {
		public ObjAttrValueContext objAttrValue() {
			return getRuleContext(ObjAttrValueContext.class,0);
		}
		public TimestampValueObjAttrValueContext(TimestampContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitTimestampValueObjAttrValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TimestampLiteralContext extends TimestampContext {
		public TerminalNode TIMESTAMP_LITERAL() { return getToken(STOCLParser.TIMESTAMP_LITERAL, 0); }
		public TimestampLiteralContext(TimestampContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitTimestampLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimestampContext timestamp() throws RecognitionException {
		TimestampContext _localctx = new TimestampContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_timestamp);
		try {
			setState(319);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TIMESTAMP_LITERAL:
				_localctx = new TimestampLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(317);
				match(TIMESTAMP_LITERAL);
				}
				break;
			case T__85:
			case ID:
				_localctx = new TimestampValueObjAttrValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(318);
				objAttrValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class SpecificationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public TerminalNode EOF() { return getToken(STOCLParser.EOF, 0); }
		public List<ContextContext> context() {
			return getRuleContexts(ContextContext.class);
		}
		public ContextContext context(int i) {
			return getRuleContext(ContextContext.class,i);
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
		enterRule(_localctx, 16, RULE_specification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			match(T__60);
			setState(322);
			match(ID);
			setState(323);
			match(T__61);
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__62) {
				{
				{
				setState(324);
				context();
				}
				}
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(330);
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
	public static class ContextContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public List<InvContext> inv() {
			return getRuleContexts(InvContext.class);
		}
		public InvContext inv(int i) {
			return getRuleContext(InvContext.class,i);
		}
		public ContextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_context; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitContext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContextContext context() throws RecognitionException {
		ContextContext _localctx = new ContextContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_context);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			match(T__62);
			setState(333);
			match(ID);
			setState(335); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(334);
				inv();
				}
				}
				setState(337); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__63 );
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
		enterRule(_localctx, 20, RULE_inv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			match(T__63);
			setState(341);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(340);
				match(ID);
				}
			}

			setState(343);
			match(T__61);
			setState(344);
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
	public static class BagPredicateContext extends ParserRuleContext {
		public BagPredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bagPredicate; }
	 
		public BagPredicateContext() { }
		public void copyFrom(BagPredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IncludesContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public IncludesContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitIncludes(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IsEmptyContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public IsEmptyContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitIsEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExcludesAllContext extends BagPredicateContext {
		public List<OclBagContext> oclBag() {
			return getRuleContexts(OclBagContext.class);
		}
		public OclBagContext oclBag(int i) {
			return getRuleContext(OclBagContext.class,i);
		}
		public ExcludesAllContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitExcludesAll(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExistsContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public ExistsContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitExists(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IsUniqueContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public IsUniqueContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitIsUnique(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OneContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public OneContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOne(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IncludesAllContext extends BagPredicateContext {
		public List<OclBagContext> oclBag() {
			return getRuleContexts(OclBagContext.class);
		}
		public OclBagContext oclBag(int i) {
			return getRuleContext(OclBagContext.class,i);
		}
		public IncludesAllContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitIncludesAll(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotEmptyContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public NotEmptyContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitNotEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExcludesContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExcludesContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitExcludes(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForAllContext extends BagPredicateContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public VarListContext varList() {
			return getRuleContext(VarListContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public ForAllContext(BagPredicateContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitForAll(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BagPredicateContext bagPredicate() throws RecognitionException {
		BagPredicateContext _localctx = new BagPredicateContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_bagPredicate);
		try {
			setState(398);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new IncludesAllContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(346);
				oclBag(0);
				setState(347);
				match(T__64);
				setState(348);
				oclBag(0);
				setState(349);
				match(T__7);
				}
				break;
			case 2:
				_localctx = new ExcludesAllContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(351);
				oclBag(0);
				setState(352);
				match(T__65);
				setState(353);
				oclBag(0);
				setState(354);
				match(T__7);
				}
				break;
			case 3:
				_localctx = new IncludesContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(356);
				oclBag(0);
				setState(357);
				match(T__66);
				setState(358);
				literal();
				setState(359);
				match(T__7);
				}
				break;
			case 4:
				_localctx = new ExcludesContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(361);
				oclBag(0);
				setState(362);
				match(T__67);
				setState(363);
				literal();
				setState(364);
				match(T__7);
				}
				break;
			case 5:
				_localctx = new IsEmptyContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(366);
				oclBag(0);
				setState(367);
				match(T__68);
				}
				break;
			case 6:
				_localctx = new NotEmptyContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(369);
				oclBag(0);
				setState(370);
				match(T__69);
				}
				break;
			case 7:
				_localctx = new ForAllContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(372);
				oclBag(0);
				setState(373);
				match(T__70);
				setState(374);
				varList();
				setState(375);
				match(T__71);
				setState(376);
				oclBool(0);
				setState(377);
				match(T__7);
				}
				break;
			case 8:
				_localctx = new ExistsContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(379);
				oclBag(0);
				setState(380);
				match(T__72);
				setState(381);
				varList();
				setState(382);
				match(T__71);
				setState(383);
				oclBool(0);
				setState(384);
				match(T__7);
				}
				break;
			case 9:
				_localctx = new OneContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(386);
				oclBag(0);
				setState(387);
				match(T__73);
				setState(388);
				var();
				setState(389);
				match(T__71);
				setState(390);
				oclBool(0);
				setState(391);
				match(T__7);
				}
				break;
			case 10:
				_localctx = new IsUniqueContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(393);
				oclBag(0);
				setState(394);
				match(T__74);
				setState(395);
				attr();
				setState(396);
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
	public static class OclBagContext extends ParserRuleContext {
		public OclBagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclBag; }
	 
		public OclBagContext() { }
		public void copyFrom(OclBagContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntersectionContext extends OclBagContext {
		public List<OclBagContext> oclBag() {
			return getRuleContexts(OclBagContext.class);
		}
		public OclBagContext oclBag(int i) {
			return getRuleContext(OclBagContext.class,i);
		}
		public IntersectionContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitIntersection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RejectContext extends OclBagContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public RejectContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitReject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BagRoleOrAttrContext extends OclBagContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public RoleOrAttrContext roleOrAttr() {
			return getRuleContext(RoleOrAttrContext.class,0);
		}
		public BagRoleOrAttrContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitBagRoleOrAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectContext extends OclBagContext {
		public OclBagContext oclBag() {
			return getRuleContext(OclBagContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public OclBoolContext oclBool() {
			return getRuleContext(OclBoolContext.class,0);
		}
		public SelectContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSelect(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SymmetricDifferenceContext extends OclBagContext {
		public List<OclBagContext> oclBag() {
			return getRuleContexts(OclBagContext.class);
		}
		public OclBagContext oclBag(int i) {
			return getRuleContext(OclBagContext.class,i);
		}
		public SymmetricDifferenceContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitSymmetricDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DifferenceContext extends OclBagContext {
		public List<OclBagContext> oclBag() {
			return getRuleContexts(OclBagContext.class);
		}
		public OclBagContext oclBag(int i) {
			return getRuleContext(OclBagContext.class,i);
		}
		public DifferenceContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AllInstancesContext extends OclBagContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public AllInstancesContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitAllInstances(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BagElementsLiteralContext extends OclBagContext {
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public BagElementsLiteralContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitBagElementsLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnionContext extends OclBagContext {
		public List<OclBagContext> oclBag() {
			return getRuleContexts(OclBagContext.class);
		}
		public OclBagContext oclBag(int i) {
			return getRuleContext(OclBagContext.class,i);
		}
		public UnionContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitUnion(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultipleRoleContext extends OclBagContext {
		public OclObjContext oclObj() {
			return getRuleContext(OclObjContext.class,0);
		}
		public RoleContext role() {
			return getRuleContext(RoleContext.class,0);
		}
		public MultipleRoleContext(OclBagContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitMultipleRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclBagContext oclBag() throws RecognitionException {
		return oclBag(0);
	}

	private OclBagContext oclBag(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OclBagContext _localctx = new OclBagContext(_ctx, _parentState);
		OclBagContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_oclBag, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				_localctx = new AllInstancesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(401);
				match(ID);
				setState(402);
				match(T__5);
				setState(403);
				match(T__82);
				}
				break;
			case 2:
				{
				_localctx = new MultipleRoleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(404);
				oclObj(0);
				setState(405);
				match(T__5);
				setState(406);
				role();
				}
				break;
			case 3:
				{
				_localctx = new BagElementsLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(408);
				match(T__83);
				setState(409);
				literal();
				setState(414);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__44) {
					{
					{
					setState(410);
					match(T__44);
					setState(411);
					literal();
					}
					}
					setState(416);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(417);
				match(T__84);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(462);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(460);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new UnionContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(421);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(422);
						match(T__75);
						setState(423);
						oclBag(0);
						setState(424);
						match(T__7);
						}
						break;
					case 2:
						{
						_localctx = new IntersectionContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(426);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(427);
						match(T__76);
						setState(428);
						oclBag(0);
						setState(429);
						match(T__7);
						}
						break;
					case 3:
						{
						_localctx = new DifferenceContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(431);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(432);
						match(T__77);
						setState(433);
						oclBag(0);
						setState(434);
						match(T__7);
						}
						break;
					case 4:
						{
						_localctx = new SymmetricDifferenceContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(436);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(437);
						match(T__78);
						setState(438);
						oclBag(0);
						setState(439);
						match(T__7);
						}
						break;
					case 5:
						{
						_localctx = new SelectContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(441);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(442);
						match(T__79);
						setState(443);
						var();
						setState(444);
						match(T__71);
						setState(445);
						oclBool(0);
						setState(446);
						match(T__7);
						}
						break;
					case 6:
						{
						_localctx = new RejectContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(448);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(449);
						match(T__80);
						setState(450);
						var();
						setState(451);
						match(T__71);
						setState(452);
						oclBool(0);
						setState(453);
						match(T__7);
						}
						break;
					case 7:
						{
						_localctx = new BagRoleOrAttrContext(new OclBagContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_oclBag);
						setState(455);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(456);
						match(T__81);
						setState(457);
						roleOrAttr();
						setState(458);
						match(T__7);
						}
						break;
					}
					} 
				}
				setState(464);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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
	public static class OclObjContext extends ParserRuleContext {
		public OclObjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oclObj; }
	 
		public OclObjContext() { }
		public void copyFrom(OclObjContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectSelfContext extends OclObjContext {
		public OclObjectSelfContext(OclObjContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectSelf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectVarContext extends OclObjContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public OclObjectVarContext(OclObjContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OclObjectRoleContext extends OclObjContext {
		public OclObjContext oclObj() {
			return getRuleContext(OclObjContext.class,0);
		}
		public RoleContext role() {
			return getRuleContext(RoleContext.class,0);
		}
		public OclObjectRoleContext(OclObjContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitOclObjectRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OclObjContext oclObj() throws RecognitionException {
		return oclObj(0);
	}

	private OclObjContext oclObj(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OclObjContext _localctx = new OclObjContext(_ctx, _parentState);
		OclObjContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_oclObj, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(468);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				_localctx = new OclObjectVarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(466);
				var();
				}
				break;
			case T__85:
				{
				_localctx = new OclObjectSelfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(467);
				match(T__85);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(475);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OclObjectRoleContext(new OclObjContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_oclObj);
					setState(470);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(471);
					match(T__5);
					setState(472);
					role();
					}
					} 
				}
				setState(477);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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
	public static class ObjAttrValueContext extends ParserRuleContext {
		public ObjAttrValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objAttrValue; }
	 
		public ObjAttrValueContext() { }
		public void copyFrom(ObjAttrValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ObjAttrValueFAttrContext extends ObjAttrValueContext {
		public OclObjContext oclObj() {
			return getRuleContext(OclObjContext.class,0);
		}
		public AttrContext attr() {
			return getRuleContext(AttrContext.class,0);
		}
		public ObjAttrValueFAttrContext(ObjAttrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitObjAttrValueFAttr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ObjAttrValueVarContext extends ObjAttrValueContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ObjAttrValueVarContext(ObjAttrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitObjAttrValueVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjAttrValueContext objAttrValue() throws RecognitionException {
		ObjAttrValueContext _localctx = new ObjAttrValueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_objAttrValue);
		try {
			setState(483);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new ObjAttrValueFAttrContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(478);
				oclObj(0);
				setState(479);
				match(T__5);
				setState(480);
				attr();
				}
				break;
			case 2:
				_localctx = new ObjAttrValueVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(482);
				var();
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
	public static class StrValueContext extends ParserRuleContext {
		public StrValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strValue; }
	 
		public StrValueContext() { }
		public void copyFrom(StrValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueObjAttrValueContext extends StrValueContext {
		public ObjAttrValueContext objAttrValue() {
			return getRuleContext(ObjAttrValueContext.class,0);
		}
		public StringValueObjAttrValueContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueObjAttrValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueToLowerCaseContext extends StrValueContext {
		public StrValueContext strValue() {
			return getRuleContext(StrValueContext.class,0);
		}
		public StringValueToLowerCaseContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueToLowerCase(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueAtContext extends StrValueContext {
		public StrValueContext strValue() {
			return getRuleContext(StrValueContext.class,0);
		}
		public TerminalNode INT_LITERAL() { return getToken(STOCLParser.INT_LITERAL, 0); }
		public StringValueAtContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueAt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueLiteralContext extends StrValueContext {
		public TerminalNode STRING_LITERAL() { return getToken(STOCLParser.STRING_LITERAL, 0); }
		public StringValueLiteralContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueConcatContext extends StrValueContext {
		public List<StrValueContext> strValue() {
			return getRuleContexts(StrValueContext.class);
		}
		public StrValueContext strValue(int i) {
			return getRuleContext(StrValueContext.class,i);
		}
		public StringValueConcatContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueConcat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueToUpperCaseContext extends StrValueContext {
		public StrValueContext strValue() {
			return getRuleContext(StrValueContext.class,0);
		}
		public StringValueToUpperCaseContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueToUpperCase(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueSubstringContext extends StrValueContext {
		public StrValueContext strValue() {
			return getRuleContext(StrValueContext.class,0);
		}
		public List<TerminalNode> INT_LITERAL() { return getTokens(STOCLParser.INT_LITERAL); }
		public TerminalNode INT_LITERAL(int i) {
			return getToken(STOCLParser.INT_LITERAL, i);
		}
		public StringValueSubstringContext(StrValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitStringValueSubstring(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrValueContext strValue() throws RecognitionException {
		return strValue(0);
	}

	private StrValueContext strValue(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StrValueContext _localctx = new StrValueContext(_ctx, _parentState);
		StrValueContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_strValue, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(488);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LITERAL:
				{
				_localctx = new StringValueLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(486);
				match(STRING_LITERAL);
				}
				break;
			case T__85:
			case ID:
				{
				_localctx = new StringValueObjAttrValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(487);
				objAttrValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(511);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(509);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new StringValueConcatContext(new StrValueContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_strValue);
						setState(490);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(491);
						match(T__86);
						setState(492);
						strValue(0);
						setState(493);
						match(T__7);
						}
						break;
					case 2:
						{
						_localctx = new StringValueSubstringContext(new StrValueContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_strValue);
						setState(495);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(496);
						match(T__87);
						setState(497);
						match(INT_LITERAL);
						setState(498);
						match(T__44);
						setState(499);
						match(INT_LITERAL);
						setState(500);
						match(T__7);
						}
						break;
					case 3:
						{
						_localctx = new StringValueToUpperCaseContext(new StrValueContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_strValue);
						setState(501);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(502);
						match(T__88);
						}
						break;
					case 4:
						{
						_localctx = new StringValueToLowerCaseContext(new StrValueContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_strValue);
						setState(503);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(504);
						match(T__89);
						}
						break;
					case 5:
						{
						_localctx = new StringValueAtContext(new StrValueContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_strValue);
						setState(505);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(506);
						match(T__90);
						setState(507);
						match(INT_LITERAL);
						setState(508);
						match(T__7);
						}
						break;
					}
					} 
				}
				setState(513);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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
	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralStringContext extends LiteralContext {
		public TerminalNode STRING_LITERAL() { return getToken(STOCLParser.STRING_LITERAL, 0); }
		public LiteralStringContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitLiteralString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralBooleanContext extends LiteralContext {
		public TerminalNode BOOLEAN_LITERAL() { return getToken(STOCLParser.BOOLEAN_LITERAL, 0); }
		public LiteralBooleanContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitLiteralBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralIntContext extends LiteralContext {
		public TerminalNode INT_LITERAL() { return getToken(STOCLParser.INT_LITERAL, 0); }
		public LiteralIntContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitLiteralInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralRealContext extends LiteralContext {
		public TerminalNode REAL_LITERAL() { return getToken(STOCLParser.REAL_LITERAL, 0); }
		public LiteralRealContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitLiteralReal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_literal);
		try {
			setState(518);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_LITERAL:
				_localctx = new LiteralIntContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(514);
				match(INT_LITERAL);
				}
				break;
			case REAL_LITERAL:
				_localctx = new LiteralRealContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(515);
				match(REAL_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new LiteralBooleanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(516);
				match(BOOLEAN_LITERAL);
				}
				break;
			case STRING_LITERAL:
				_localctx = new LiteralStringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(517);
				match(STRING_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		enterRule(_localctx, 34, RULE_varList);
		int _la;
		try {
			_localctx = new VarListValueContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(520);
			var();
			setState(525);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__44) {
				{
				{
				setState(521);
				match(T__44);
				setState(522);
				var();
				}
				}
				setState(527);
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
		enterRule(_localctx, 36, RULE_var);
		try {
			_localctx = new VarIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(528);
			match(ID);
			setState(531);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(529);
				match(T__61);
				setState(530);
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
		enterRule(_localctx, 38, RULE_roleOrAttr);
		try {
			_localctx = new RoleOrAttrIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(533);
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
	public static class RoleContext extends ParserRuleContext {
		public RoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_role; }
	 
		public RoleContext() { }
		public void copyFrom(RoleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RoleIDContext extends RoleContext {
		public TerminalNode ID() { return getToken(STOCLParser.ID, 0); }
		public RoleIDContext(RoleContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STOCLVisitor ) return ((STOCLVisitor<? extends T>)visitor).visitRoleID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoleContext role() throws RecognitionException {
		RoleContext _localctx = new RoleContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_role);
		try {
			_localctx = new RoleIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(535);
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
		enterRule(_localctx, 42, RULE_attr);
		try {
			_localctx = new AttrIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(537);
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
		enterRule(_localctx, 44, RULE_bAttr);
		try {
			_localctx = new BAttrIDContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(539);
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
		case 0:
			return oclBool_sempred((OclBoolContext)_localctx, predIndex);
		case 2:
			return arithExpr_sempred((ArithExprContext)_localctx, predIndex);
		case 4:
			return geom_sempred((GeomContext)_localctx, predIndex);
		case 12:
			return oclBag_sempred((OclBagContext)_localctx, predIndex);
		case 13:
			return oclObj_sempred((OclObjContext)_localctx, predIndex);
		case 15:
			return strValue_sempred((StrValueContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean oclBool_sempred(OclBoolContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		}
		return true;
	}
	private boolean arithExpr_sempred(ArithExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
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
		case 10:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean geom_sempred(GeomContext _localctx, int predIndex) {
		switch (predIndex) {
		case 11:
			return precpred(_ctx, 8);
		case 12:
			return precpred(_ctx, 7);
		case 13:
			return precpred(_ctx, 6);
		case 14:
			return precpred(_ctx, 5);
		case 15:
			return precpred(_ctx, 4);
		case 16:
			return precpred(_ctx, 3);
		case 17:
			return precpred(_ctx, 2);
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean oclBag_sempred(OclBagContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 10);
		case 20:
			return precpred(_ctx, 9);
		case 21:
			return precpred(_ctx, 8);
		case 22:
			return precpred(_ctx, 7);
		case 23:
			return precpred(_ctx, 6);
		case 24:
			return precpred(_ctx, 5);
		case 25:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean oclObj_sempred(OclObjContext _localctx, int predIndex) {
		switch (predIndex) {
		case 26:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean strValue_sempred(StrValueContext _localctx, int predIndex) {
		switch (predIndex) {
		case 27:
			return precpred(_ctx, 5);
		case 28:
			return precpred(_ctx, 4);
		case 29:
			return precpred(_ctx, 3);
		case 30:
			return precpred(_ctx, 2);
		case 31:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001j\u021e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000"+
		">\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000F\b\u0000\n\u0000\f\u0000I\t\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001_\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002v\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002\u0098\b\u0002"+
		"\n\u0002\f\u0002\u009b\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003\u00e2\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0003\u0004\u00e9\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u0109\b\u0004\n\u0004\f\u0004"+
		"\u010c\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"\u0136\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0003\u0007\u0140\b\u0007\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0005\b\u0146\b\b\n\b\f\b\u0149\t\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0004\t\u0150\b\t\u000b\t\f\t\u0151\u0001\n"+
		"\u0001\n\u0003\n\u0156\b\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u018f\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0005\f\u019d\b\f\n\f\f\f\u01a0\t\f\u0001\f\u0001\f\u0003\f\u01a4\b"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0005\f\u01cd\b\f\n\f\f\f\u01d0\t\f\u0001\r"+
		"\u0001\r\u0001\r\u0003\r\u01d5\b\r\u0001\r\u0001\r\u0001\r\u0005\r\u01da"+
		"\b\r\n\r\f\r\u01dd\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u01e4\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0003\u000f\u01e9\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u01fe\b\u000f\n\u000f"+
		"\f\u000f\u0201\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0003\u0010\u0207\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011"+
		"\u020c\b\u0011\n\u0011\f\u0011\u020f\t\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u0214\b\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0000"+
		"\u0006\u0000\u0004\b\u0018\u001a\u001e\u0017\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,\u0000"+
		"\n\u0001\u0000\u0002\u0003\u0001\u0000\u0004\u0005\u0001\u0000\t\u000e"+
		"\u0002\u0000\u000b\u000b\u000e\u000e\u0001\u0000\u0013\u0017\u0001\u0000"+
		"\u0010\u0011\u0002\u0000\u000f\u000f\u0012\u0012\u0001\u0000`a\u0002\u0000"+
		"\u0007\u0007;;\u0002\u0000\b\b<<\u0265\u0000=\u0001\u0000\u0000\u0000"+
		"\u0002^\u0001\u0000\u0000\u0000\u0004u\u0001\u0000\u0000\u0000\u0006\u00e1"+
		"\u0001\u0000\u0000\u0000\b\u00e8\u0001\u0000\u0000\u0000\n\u0135\u0001"+
		"\u0000\u0000\u0000\f\u0137\u0001\u0000\u0000\u0000\u000e\u013f\u0001\u0000"+
		"\u0000\u0000\u0010\u0141\u0001\u0000\u0000\u0000\u0012\u014c\u0001\u0000"+
		"\u0000\u0000\u0014\u0153\u0001\u0000\u0000\u0000\u0016\u018e\u0001\u0000"+
		"\u0000\u0000\u0018\u01a3\u0001\u0000\u0000\u0000\u001a\u01d4\u0001\u0000"+
		"\u0000\u0000\u001c\u01e3\u0001\u0000\u0000\u0000\u001e\u01e8\u0001\u0000"+
		"\u0000\u0000 \u0206\u0001\u0000\u0000\u0000\"\u0208\u0001\u0000\u0000"+
		"\u0000$\u0210\u0001\u0000\u0000\u0000&\u0215\u0001\u0000\u0000\u0000("+
		"\u0217\u0001\u0000\u0000\u0000*\u0219\u0001\u0000\u0000\u0000,\u021b\u0001"+
		"\u0000\u0000\u0000./\u0006\u0000\uffff\uffff\u0000/0\u0005\u0001\u0000"+
		"\u00000>\u0003\u0000\u0000\t1>\u0003\u0002\u0001\u000023\u0003\u001a\r"+
		"\u000034\u0005\u0006\u0000\u000045\u0003,\u0016\u00005>\u0001\u0000\u0000"+
		"\u00006>\u0003\u0016\u000b\u000078\u0005\u0007\u0000\u000089\u0003\u0000"+
		"\u0000\u00009:\u0005\b\u0000\u0000:>\u0001\u0000\u0000\u0000;>\u0003\u0006"+
		"\u0003\u0000<>\u0003\n\u0005\u0000=.\u0001\u0000\u0000\u0000=1\u0001\u0000"+
		"\u0000\u0000=2\u0001\u0000\u0000\u0000=6\u0001\u0000\u0000\u0000=7\u0001"+
		"\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=<\u0001\u0000\u0000\u0000"+
		">G\u0001\u0000\u0000\u0000?@\n\b\u0000\u0000@A\u0007\u0000\u0000\u0000"+
		"AF\u0003\u0000\u0000\tBC\n\u0007\u0000\u0000CD\u0007\u0001\u0000\u0000"+
		"DF\u0003\u0000\u0000\bE?\u0001\u0000\u0000\u0000EB\u0001\u0000\u0000\u0000"+
		"FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000"+
		"\u0000H\u0001\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000JK\u0003"+
		"\u001c\u000e\u0000KL\u0007\u0002\u0000\u0000LM\u0003\u001c\u000e\u0000"+
		"M_\u0001\u0000\u0000\u0000NO\u0003\u0004\u0002\u0000OP\u0007\u0002\u0000"+
		"\u0000PQ\u0003\u0004\u0002\u0000Q_\u0001\u0000\u0000\u0000RS\u0003\u001e"+
		"\u000f\u0000ST\u0007\u0003\u0000\u0000TU\u0003\u001e\u000f\u0000U_\u0001"+
		"\u0000\u0000\u0000VW\u0003\u001a\r\u0000WX\u0007\u0003\u0000\u0000XY\u0003"+
		"\u001a\r\u0000Y_\u0001\u0000\u0000\u0000Z[\u0003\u000e\u0007\u0000[\\"+
		"\u0007\u0002\u0000\u0000\\]\u0003\u000e\u0007\u0000]_\u0001\u0000\u0000"+
		"\u0000^J\u0001\u0000\u0000\u0000^N\u0001\u0000\u0000\u0000^R\u0001\u0000"+
		"\u0000\u0000^V\u0001\u0000\u0000\u0000^Z\u0001\u0000\u0000\u0000_\u0003"+
		"\u0001\u0000\u0000\u0000`a\u0006\u0002\uffff\uffff\u0000ab\u0005\u000f"+
		"\u0000\u0000bv\u0003\u0004\u0002\u0011cd\u0005\u0007\u0000\u0000de\u0003"+
		"\u0004\u0002\u0000ef\u0005\b\u0000\u0000fv\u0001\u0000\u0000\u0000gv\u0005"+
		"`\u0000\u0000hv\u0005a\u0000\u0000iv\u0003\u001c\u000e\u0000jk\u0003\u0018"+
		"\f\u0000kl\u0007\u0004\u0000\u0000lv\u0001\u0000\u0000\u0000mn\u0003\u001e"+
		"\u000f\u0000no\u0005\u0018\u0000\u0000ov\u0001\u0000\u0000\u0000pq\u0003"+
		"\b\u0004\u0000qr\u0005 \u0000\u0000rs\u0003\b\u0004\u0000st\u0005\b\u0000"+
		"\u0000tv\u0001\u0000\u0000\u0000u`\u0001\u0000\u0000\u0000uc\u0001\u0000"+
		"\u0000\u0000ug\u0001\u0000\u0000\u0000uh\u0001\u0000\u0000\u0000ui\u0001"+
		"\u0000\u0000\u0000uj\u0001\u0000\u0000\u0000um\u0001\u0000\u0000\u0000"+
		"up\u0001\u0000\u0000\u0000v\u0099\u0001\u0000\u0000\u0000wx\n\u0010\u0000"+
		"\u0000xy\u0007\u0005\u0000\u0000y\u0098\u0003\u0004\u0002\u0011z{\n\u000f"+
		"\u0000\u0000{|\u0007\u0006\u0000\u0000|\u0098\u0003\u0004\u0002\u0010"+
		"}~\n\b\u0000\u0000~\u0098\u0005\u0019\u0000\u0000\u007f\u0080\n\u0007"+
		"\u0000\u0000\u0080\u0098\u0005\u001a\u0000\u0000\u0081\u0082\n\u0006\u0000"+
		"\u0000\u0082\u0098\u0005\u001b\u0000\u0000\u0083\u0084\n\u0005\u0000\u0000"+
		"\u0084\u0085\u0005\u001c\u0000\u0000\u0085\u0086\u0003\u0004\u0002\u0000"+
		"\u0086\u0087\u0005\b\u0000\u0000\u0087\u0098\u0001\u0000\u0000\u0000\u0088"+
		"\u0089\n\u0004\u0000\u0000\u0089\u008a\u0005\u001d\u0000\u0000\u008a\u008b"+
		"\u0003\u0004\u0002\u0000\u008b\u008c\u0005\b\u0000\u0000\u008c\u0098\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\n\u0003\u0000\u0000\u008e\u008f\u0005\u001e"+
		"\u0000\u0000\u008f\u0090\u0003\u0004\u0002\u0000\u0090\u0091\u0005\b\u0000"+
		"\u0000\u0091\u0098\u0001\u0000\u0000\u0000\u0092\u0093\n\u0002\u0000\u0000"+
		"\u0093\u0094\u0005\u001f\u0000\u0000\u0094\u0095\u0003\u0004\u0002\u0000"+
		"\u0095\u0096\u0005\b\u0000\u0000\u0096\u0098\u0001\u0000\u0000\u0000\u0097"+
		"w\u0001\u0000\u0000\u0000\u0097z\u0001\u0000\u0000\u0000\u0097}\u0001"+
		"\u0000\u0000\u0000\u0097\u007f\u0001\u0000\u0000\u0000\u0097\u0081\u0001"+
		"\u0000\u0000\u0000\u0097\u0083\u0001\u0000\u0000\u0000\u0097\u0088\u0001"+
		"\u0000\u0000\u0000\u0097\u008d\u0001\u0000\u0000\u0000\u0097\u0092\u0001"+
		"\u0000\u0000\u0000\u0098\u009b\u0001\u0000\u0000\u0000\u0099\u0097\u0001"+
		"\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u0005\u0001"+
		"\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009c\u009d\u0003"+
		"\b\u0004\u0000\u009d\u009e\u0005!\u0000\u0000\u009e\u009f\u0003\b\u0004"+
		"\u0000\u009f\u00a0\u0005\b\u0000\u0000\u00a0\u00e2\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a2\u0003\b\u0004\u0000\u00a2\u00a3\u0005\"\u0000\u0000\u00a3"+
		"\u00a4\u0003\b\u0004\u0000\u00a4\u00a5\u0005\b\u0000\u0000\u00a5\u00e2"+
		"\u0001\u0000\u0000\u0000\u00a6\u00a7\u0003\b\u0004\u0000\u00a7\u00a8\u0005"+
		"#\u0000\u0000\u00a8\u00a9\u0003\b\u0004\u0000\u00a9\u00aa\u0005\b\u0000"+
		"\u0000\u00aa\u00e2\u0001\u0000\u0000\u0000\u00ab\u00ac\u0003\b\u0004\u0000"+
		"\u00ac\u00ad\u0005$\u0000\u0000\u00ad\u00ae\u0003\b\u0004\u0000\u00ae"+
		"\u00af\u0005\b\u0000\u0000\u00af\u00e2\u0001\u0000\u0000\u0000\u00b0\u00b1"+
		"\u0003\b\u0004\u0000\u00b1\u00b2\u0005%\u0000\u0000\u00b2\u00b3\u0003"+
		"\b\u0004\u0000\u00b3\u00b4\u0005\b\u0000\u0000\u00b4\u00e2\u0001\u0000"+
		"\u0000\u0000\u00b5\u00b6\u0003\b\u0004\u0000\u00b6\u00b7\u0005&\u0000"+
		"\u0000\u00b7\u00b8\u0003\b\u0004\u0000\u00b8\u00b9\u0005\b\u0000\u0000"+
		"\u00b9\u00e2\u0001\u0000\u0000\u0000\u00ba\u00bb\u0003\b\u0004\u0000\u00bb"+
		"\u00bc\u0005\'\u0000\u0000\u00bc\u00bd\u0003\b\u0004\u0000\u00bd\u00be"+
		"\u0005\b\u0000\u0000\u00be\u00e2\u0001\u0000\u0000\u0000\u00bf\u00c0\u0003"+
		"\b\u0004\u0000\u00c0\u00c1\u0005(\u0000\u0000\u00c1\u00c2\u0003\b\u0004"+
		"\u0000\u00c2\u00c3\u0005\b\u0000\u0000\u00c3\u00e2\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c5\u0003\b\u0004\u0000\u00c5\u00c6\u0005)\u0000\u0000\u00c6"+
		"\u00c7\u0003\b\u0004\u0000\u00c7\u00c8\u0005\b\u0000\u0000\u00c8\u00e2"+
		"\u0001\u0000\u0000\u0000\u00c9\u00ca\u0003\b\u0004\u0000\u00ca\u00cb\u0005"+
		"*\u0000\u0000\u00cb\u00cc\u0003\b\u0004\u0000\u00cc\u00cd\u0005\b\u0000"+
		"\u0000\u00cd\u00e2\u0001\u0000\u0000\u0000\u00ce\u00cf\u0003\b\u0004\u0000"+
		"\u00cf\u00d0\u0005+\u0000\u0000\u00d0\u00d1\u0003\b\u0004\u0000\u00d1"+
		"\u00d2\u0005\b\u0000\u0000\u00d2\u00e2\u0001\u0000\u0000\u0000\u00d3\u00d4"+
		"\u0003\b\u0004\u0000\u00d4\u00d5\u0005,\u0000\u0000\u00d5\u00d6\u0003"+
		"\b\u0004\u0000\u00d6\u00d7\u0005-\u0000\u0000\u00d7\u00d8\u0007\u0007"+
		"\u0000\u0000\u00d8\u00d9\u0005\b\u0000\u0000\u00d9\u00e2\u0001\u0000\u0000"+
		"\u0000\u00da\u00db\u0003\b\u0004\u0000\u00db\u00dc\u0005.\u0000\u0000"+
		"\u00dc\u00dd\u0003\b\u0004\u0000\u00dd\u00de\u0005-\u0000\u0000\u00de"+
		"\u00df\u0005b\u0000\u0000\u00df\u00e0\u0005\b\u0000\u0000\u00e0\u00e2"+
		"\u0001\u0000\u0000\u0000\u00e1\u009c\u0001\u0000\u0000\u0000\u00e1\u00a1"+
		"\u0001\u0000\u0000\u0000\u00e1\u00a6\u0001\u0000\u0000\u0000\u00e1\u00ab"+
		"\u0001\u0000\u0000\u0000\u00e1\u00b0\u0001\u0000\u0000\u0000\u00e1\u00b5"+
		"\u0001\u0000\u0000\u0000\u00e1\u00ba\u0001\u0000\u0000\u0000\u00e1\u00bf"+
		"\u0001\u0000\u0000\u0000\u00e1\u00c4\u0001\u0000\u0000\u0000\u00e1\u00c9"+
		"\u0001\u0000\u0000\u0000\u00e1\u00ce\u0001\u0000\u0000\u0000\u00e1\u00d3"+
		"\u0001\u0000\u0000\u0000\u00e1\u00da\u0001\u0000\u0000\u0000\u00e2\u0007"+
		"\u0001\u0000\u0000\u0000\u00e3\u00e4\u0006\u0004\uffff\uffff\u0000\u00e4"+
		"\u00e9\u0005\\\u0000\u0000\u00e5\u00e9\u0005]\u0000\u0000\u00e6\u00e9"+
		"\u0005^\u0000\u0000\u00e7\u00e9\u0003\u001c\u000e\u0000\u00e8\u00e3\u0001"+
		"\u0000\u0000\u0000\u00e8\u00e5\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001"+
		"\u0000\u0000\u0000\u00e8\u00e7\u0001\u0000\u0000\u0000\u00e9\u010a\u0001"+
		"\u0000\u0000\u0000\u00ea\u00eb\n\b\u0000\u0000\u00eb\u00ec\u0005/\u0000"+
		"\u0000\u00ec\u00ed\u0007\u0007\u0000\u0000\u00ed\u0109\u0005\b\u0000\u0000"+
		"\u00ee\u00ef\n\u0007\u0000\u0000\u00ef\u00f0\u00050\u0000\u0000\u00f0"+
		"\u00f1\u0003\b\u0004\u0000\u00f1\u00f2\u0005\b\u0000\u0000\u00f2\u0109"+
		"\u0001\u0000\u0000\u0000\u00f3\u00f4\n\u0006\u0000\u0000\u00f4\u00f5\u0005"+
		"1\u0000\u0000\u00f5\u00f6\u0003\b\u0004\u0000\u00f6\u00f7\u0005\b\u0000"+
		"\u0000\u00f7\u0109\u0001\u0000\u0000\u0000\u00f8\u00f9\n\u0005\u0000\u0000"+
		"\u00f9\u00fa\u00052\u0000\u0000\u00fa\u00fb\u0003\b\u0004\u0000\u00fb"+
		"\u00fc\u0005\b\u0000\u0000\u00fc\u0109\u0001\u0000\u0000\u0000\u00fd\u00fe"+
		"\n\u0004\u0000\u0000\u00fe\u00ff\u00053\u0000\u0000\u00ff\u0100\u0003"+
		"\b\u0004\u0000\u0100\u0101\u0005\b\u0000\u0000\u0101\u0109\u0001\u0000"+
		"\u0000\u0000\u0102\u0103\n\u0003\u0000\u0000\u0103\u0109\u00054\u0000"+
		"\u0000\u0104\u0105\n\u0002\u0000\u0000\u0105\u0109\u00055\u0000\u0000"+
		"\u0106\u0107\n\u0001\u0000\u0000\u0107\u0109\u00056\u0000\u0000\u0108"+
		"\u00ea\u0001\u0000\u0000\u0000\u0108\u00ee\u0001\u0000\u0000\u0000\u0108"+
		"\u00f3\u0001\u0000\u0000\u0000\u0108\u00f8\u0001\u0000\u0000\u0000\u0108"+
		"\u00fd\u0001\u0000\u0000\u0000\u0108\u0102\u0001\u0000\u0000\u0000\u0108"+
		"\u0104\u0001\u0000\u0000\u0000\u0108\u0106\u0001\u0000\u0000\u0000\u0109"+
		"\u010c\u0001\u0000\u0000\u0000\u010a\u0108\u0001\u0000\u0000\u0000\u010a"+
		"\u010b\u0001\u0000\u0000\u0000\u010b\t\u0001\u0000\u0000\u0000\u010c\u010a"+
		"\u0001\u0000\u0000\u0000\u010d\u010e\u0003\f\u0006\u0000\u010e\u010f\u0005"+
		"!\u0000\u0000\u010f\u0110\u0003\u000e\u0007\u0000\u0110\u0111\u0005\b"+
		"\u0000\u0000\u0111\u0136\u0001\u0000\u0000\u0000\u0112\u0113\u0003\f\u0006"+
		"\u0000\u0113\u0114\u0005!\u0000\u0000\u0114\u0115\u0003\f\u0006\u0000"+
		"\u0115\u0116\u0005\b\u0000\u0000\u0116\u0136\u0001\u0000\u0000\u0000\u0117"+
		"\u0118\u0003\f\u0006\u0000\u0118\u0119\u0005)\u0000\u0000\u0119\u011a"+
		"\u0003\f\u0006\u0000\u011a\u011b\u0005\b\u0000\u0000\u011b\u0136\u0001"+
		"\u0000\u0000\u0000\u011c\u011d\u0003\f\u0006\u0000\u011d\u011e\u0005\'"+
		"\u0000\u0000\u011e\u011f\u0003\f\u0006\u0000\u011f\u0120\u0005\b\u0000"+
		"\u0000\u0120\u0136\u0001\u0000\u0000\u0000\u0121\u0122\u0003\f\u0006\u0000"+
		"\u0122\u0123\u00057\u0000\u0000\u0123\u0124\u0003\f\u0006\u0000\u0124"+
		"\u0125\u0005\b\u0000\u0000\u0125\u0136\u0001\u0000\u0000\u0000\u0126\u0127"+
		"\u0003\f\u0006\u0000\u0127\u0128\u00058\u0000\u0000\u0128\u0129\u0003"+
		"\f\u0006\u0000\u0129\u012a\u0005\b\u0000\u0000\u012a\u0136\u0001\u0000"+
		"\u0000\u0000\u012b\u012c\u0003\f\u0006\u0000\u012c\u012d\u00059\u0000"+
		"\u0000\u012d\u012e\u0003\f\u0006\u0000\u012e\u012f\u0005\b\u0000\u0000"+
		"\u012f\u0136\u0001\u0000\u0000\u0000\u0130\u0131\u0003\f\u0006\u0000\u0131"+
		"\u0132\u0005:\u0000\u0000\u0132\u0133\u0003\f\u0006\u0000\u0133\u0134"+
		"\u0005\b\u0000\u0000\u0134\u0136\u0001\u0000\u0000\u0000\u0135\u010d\u0001"+
		"\u0000\u0000\u0000\u0135\u0112\u0001\u0000\u0000\u0000\u0135\u0117\u0001"+
		"\u0000\u0000\u0000\u0135\u011c\u0001\u0000\u0000\u0000\u0135\u0121\u0001"+
		"\u0000\u0000\u0000\u0135\u0126\u0001\u0000\u0000\u0000\u0135\u012b\u0001"+
		"\u0000\u0000\u0000\u0135\u0130\u0001\u0000\u0000\u0000\u0136\u000b\u0001"+
		"\u0000\u0000\u0000\u0137\u0138\u0007\b\u0000\u0000\u0138\u0139\u0003\u000e"+
		"\u0007\u0000\u0139\u013a\u0005-\u0000\u0000\u013a\u013b\u0003\u000e\u0007"+
		"\u0000\u013b\u013c\u0007\t\u0000\u0000\u013c\r\u0001\u0000\u0000\u0000"+
		"\u013d\u0140\u0005_\u0000\u0000\u013e\u0140\u0003\u001c\u000e\u0000\u013f"+
		"\u013d\u0001\u0000\u0000\u0000\u013f\u013e\u0001\u0000\u0000\u0000\u0140"+
		"\u000f\u0001\u0000\u0000\u0000\u0141\u0142\u0005=\u0000\u0000\u0142\u0143"+
		"\u0005i\u0000\u0000\u0143\u0147\u0005>\u0000\u0000\u0144\u0146\u0003\u0012"+
		"\t\u0000\u0145\u0144\u0001\u0000\u0000\u0000\u0146\u0149\u0001\u0000\u0000"+
		"\u0000\u0147\u0145\u0001\u0000\u0000\u0000\u0147\u0148\u0001\u0000\u0000"+
		"\u0000\u0148\u014a\u0001\u0000\u0000\u0000\u0149\u0147\u0001\u0000\u0000"+
		"\u0000\u014a\u014b\u0005\u0000\u0000\u0001\u014b\u0011\u0001\u0000\u0000"+
		"\u0000\u014c\u014d\u0005?\u0000\u0000\u014d\u014f\u0005i\u0000\u0000\u014e"+
		"\u0150\u0003\u0014\n\u0000\u014f\u014e\u0001\u0000\u0000\u0000\u0150\u0151"+
		"\u0001\u0000\u0000\u0000\u0151\u014f\u0001\u0000\u0000\u0000\u0151\u0152"+
		"\u0001\u0000\u0000\u0000\u0152\u0013\u0001\u0000\u0000\u0000\u0153\u0155"+
		"\u0005@\u0000\u0000\u0154\u0156\u0005i\u0000\u0000\u0155\u0154\u0001\u0000"+
		"\u0000\u0000\u0155\u0156\u0001\u0000\u0000\u0000\u0156\u0157\u0001\u0000"+
		"\u0000\u0000\u0157\u0158\u0005>\u0000\u0000\u0158\u0159\u0003\u0000\u0000"+
		"\u0000\u0159\u0015\u0001\u0000\u0000\u0000\u015a\u015b\u0003\u0018\f\u0000"+
		"\u015b\u015c\u0005A\u0000\u0000\u015c\u015d\u0003\u0018\f\u0000\u015d"+
		"\u015e\u0005\b\u0000\u0000\u015e\u018f\u0001\u0000\u0000\u0000\u015f\u0160"+
		"\u0003\u0018\f\u0000\u0160\u0161\u0005B\u0000\u0000\u0161\u0162\u0003"+
		"\u0018\f\u0000\u0162\u0163\u0005\b\u0000\u0000\u0163\u018f\u0001\u0000"+
		"\u0000\u0000\u0164\u0165\u0003\u0018\f\u0000\u0165\u0166\u0005C\u0000"+
		"\u0000\u0166\u0167\u0003 \u0010\u0000\u0167\u0168\u0005\b\u0000\u0000"+
		"\u0168\u018f\u0001\u0000\u0000\u0000\u0169\u016a\u0003\u0018\f\u0000\u016a"+
		"\u016b\u0005D\u0000\u0000\u016b\u016c\u0003 \u0010\u0000\u016c\u016d\u0005"+
		"\b\u0000\u0000\u016d\u018f\u0001\u0000\u0000\u0000\u016e\u016f\u0003\u0018"+
		"\f\u0000\u016f\u0170\u0005E\u0000\u0000\u0170\u018f\u0001\u0000\u0000"+
		"\u0000\u0171\u0172\u0003\u0018\f\u0000\u0172\u0173\u0005F\u0000\u0000"+
		"\u0173\u018f\u0001\u0000\u0000\u0000\u0174\u0175\u0003\u0018\f\u0000\u0175"+
		"\u0176\u0005G\u0000\u0000\u0176\u0177\u0003\"\u0011\u0000\u0177\u0178"+
		"\u0005H\u0000\u0000\u0178\u0179\u0003\u0000\u0000\u0000\u0179\u017a\u0005"+
		"\b\u0000\u0000\u017a\u018f\u0001\u0000\u0000\u0000\u017b\u017c\u0003\u0018"+
		"\f\u0000\u017c\u017d\u0005I\u0000\u0000\u017d\u017e\u0003\"\u0011\u0000"+
		"\u017e\u017f\u0005H\u0000\u0000\u017f\u0180\u0003\u0000\u0000\u0000\u0180"+
		"\u0181\u0005\b\u0000\u0000\u0181\u018f\u0001\u0000\u0000\u0000\u0182\u0183"+
		"\u0003\u0018\f\u0000\u0183\u0184\u0005J\u0000\u0000\u0184\u0185\u0003"+
		"$\u0012\u0000\u0185\u0186\u0005H\u0000\u0000\u0186\u0187\u0003\u0000\u0000"+
		"\u0000\u0187\u0188\u0005\b\u0000\u0000\u0188\u018f\u0001\u0000\u0000\u0000"+
		"\u0189\u018a\u0003\u0018\f\u0000\u018a\u018b\u0005K\u0000\u0000\u018b"+
		"\u018c\u0003*\u0015\u0000\u018c\u018d\u0005\b\u0000\u0000\u018d\u018f"+
		"\u0001\u0000\u0000\u0000\u018e\u015a\u0001\u0000\u0000\u0000\u018e\u015f"+
		"\u0001\u0000\u0000\u0000\u018e\u0164\u0001\u0000\u0000\u0000\u018e\u0169"+
		"\u0001\u0000\u0000\u0000\u018e\u016e\u0001\u0000\u0000\u0000\u018e\u0171"+
		"\u0001\u0000\u0000\u0000\u018e\u0174\u0001\u0000\u0000\u0000\u018e\u017b"+
		"\u0001\u0000\u0000\u0000\u018e\u0182\u0001\u0000\u0000\u0000\u018e\u0189"+
		"\u0001\u0000\u0000\u0000\u018f\u0017\u0001\u0000\u0000\u0000\u0190\u0191"+
		"\u0006\f\uffff\uffff\u0000\u0191\u0192\u0005i\u0000\u0000\u0192\u0193"+
		"\u0005\u0006\u0000\u0000\u0193\u01a4\u0005S\u0000\u0000\u0194\u0195\u0003"+
		"\u001a\r\u0000\u0195\u0196\u0005\u0006\u0000\u0000\u0196\u0197\u0003("+
		"\u0014\u0000\u0197\u01a4\u0001\u0000\u0000\u0000\u0198\u0199\u0005T\u0000"+
		"\u0000\u0199\u019e\u0003 \u0010\u0000\u019a\u019b\u0005-\u0000\u0000\u019b"+
		"\u019d\u0003 \u0010\u0000\u019c\u019a\u0001\u0000\u0000\u0000\u019d\u01a0"+
		"\u0001\u0000\u0000\u0000\u019e\u019c\u0001\u0000\u0000\u0000\u019e\u019f"+
		"\u0001\u0000\u0000\u0000\u019f\u01a1\u0001\u0000\u0000\u0000\u01a0\u019e"+
		"\u0001\u0000\u0000\u0000\u01a1\u01a2\u0005U\u0000\u0000\u01a2\u01a4\u0001"+
		"\u0000\u0000\u0000\u01a3\u0190\u0001\u0000\u0000\u0000\u01a3\u0194\u0001"+
		"\u0000\u0000\u0000\u01a3\u0198\u0001\u0000\u0000\u0000\u01a4\u01ce\u0001"+
		"\u0000\u0000\u0000\u01a5\u01a6\n\n\u0000\u0000\u01a6\u01a7\u0005L\u0000"+
		"\u0000\u01a7\u01a8\u0003\u0018\f\u0000\u01a8\u01a9\u0005\b\u0000\u0000"+
		"\u01a9\u01cd\u0001\u0000\u0000\u0000\u01aa\u01ab\n\t\u0000\u0000\u01ab"+
		"\u01ac\u0005M\u0000\u0000\u01ac\u01ad\u0003\u0018\f\u0000\u01ad\u01ae"+
		"\u0005\b\u0000\u0000\u01ae\u01cd\u0001\u0000\u0000\u0000\u01af\u01b0\n"+
		"\b\u0000\u0000\u01b0\u01b1\u0005N\u0000\u0000\u01b1\u01b2\u0003\u0018"+
		"\f\u0000\u01b2\u01b3\u0005\b\u0000\u0000\u01b3\u01cd\u0001\u0000\u0000"+
		"\u0000\u01b4\u01b5\n\u0007\u0000\u0000\u01b5\u01b6\u0005O\u0000\u0000"+
		"\u01b6\u01b7\u0003\u0018\f\u0000\u01b7\u01b8\u0005\b\u0000\u0000\u01b8"+
		"\u01cd\u0001\u0000\u0000\u0000\u01b9\u01ba\n\u0006\u0000\u0000\u01ba\u01bb"+
		"\u0005P\u0000\u0000\u01bb\u01bc\u0003$\u0012\u0000\u01bc\u01bd\u0005H"+
		"\u0000\u0000\u01bd\u01be\u0003\u0000\u0000\u0000\u01be\u01bf\u0005\b\u0000"+
		"\u0000\u01bf\u01cd\u0001\u0000\u0000\u0000\u01c0\u01c1\n\u0005\u0000\u0000"+
		"\u01c1\u01c2\u0005Q\u0000\u0000\u01c2\u01c3\u0003$\u0012\u0000\u01c3\u01c4"+
		"\u0005H\u0000\u0000\u01c4\u01c5\u0003\u0000\u0000\u0000\u01c5\u01c6\u0005"+
		"\b\u0000\u0000\u01c6\u01cd\u0001\u0000\u0000\u0000\u01c7\u01c8\n\u0004"+
		"\u0000\u0000\u01c8\u01c9\u0005R\u0000\u0000\u01c9\u01ca\u0003&\u0013\u0000"+
		"\u01ca\u01cb\u0005\b\u0000\u0000\u01cb\u01cd\u0001\u0000\u0000\u0000\u01cc"+
		"\u01a5\u0001\u0000\u0000\u0000\u01cc\u01aa\u0001\u0000\u0000\u0000\u01cc"+
		"\u01af\u0001\u0000\u0000\u0000\u01cc\u01b4\u0001\u0000\u0000\u0000\u01cc"+
		"\u01b9\u0001\u0000\u0000\u0000\u01cc\u01c0\u0001\u0000\u0000\u0000\u01cc"+
		"\u01c7\u0001\u0000\u0000\u0000\u01cd\u01d0\u0001\u0000\u0000\u0000\u01ce"+
		"\u01cc\u0001\u0000\u0000\u0000\u01ce\u01cf\u0001\u0000\u0000\u0000\u01cf"+
		"\u0019\u0001\u0000\u0000\u0000\u01d0\u01ce\u0001\u0000\u0000\u0000\u01d1"+
		"\u01d2\u0006\r\uffff\uffff\u0000\u01d2\u01d5\u0003$\u0012\u0000\u01d3"+
		"\u01d5\u0005V\u0000\u0000\u01d4\u01d1\u0001\u0000\u0000\u0000\u01d4\u01d3"+
		"\u0001\u0000\u0000\u0000\u01d5\u01db\u0001\u0000\u0000\u0000\u01d6\u01d7"+
		"\n\u0003\u0000\u0000\u01d7\u01d8\u0005\u0006\u0000\u0000\u01d8\u01da\u0003"+
		"(\u0014\u0000\u01d9\u01d6\u0001\u0000\u0000\u0000\u01da\u01dd\u0001\u0000"+
		"\u0000\u0000\u01db\u01d9\u0001\u0000\u0000\u0000\u01db\u01dc\u0001\u0000"+
		"\u0000\u0000\u01dc\u001b\u0001\u0000\u0000\u0000\u01dd\u01db\u0001\u0000"+
		"\u0000\u0000\u01de\u01df\u0003\u001a\r\u0000\u01df\u01e0\u0005\u0006\u0000"+
		"\u0000\u01e0\u01e1\u0003*\u0015\u0000\u01e1\u01e4\u0001\u0000\u0000\u0000"+
		"\u01e2\u01e4\u0003$\u0012\u0000\u01e3\u01de\u0001\u0000\u0000\u0000\u01e3"+
		"\u01e2\u0001\u0000\u0000\u0000\u01e4\u001d\u0001\u0000\u0000\u0000\u01e5"+
		"\u01e6\u0006\u000f\uffff\uffff\u0000\u01e6\u01e9\u0005b\u0000\u0000\u01e7"+
		"\u01e9\u0003\u001c\u000e\u0000\u01e8\u01e5\u0001\u0000\u0000\u0000\u01e8"+
		"\u01e7\u0001\u0000\u0000\u0000\u01e9\u01ff\u0001\u0000\u0000\u0000\u01ea"+
		"\u01eb\n\u0005\u0000\u0000\u01eb\u01ec\u0005W\u0000\u0000\u01ec\u01ed"+
		"\u0003\u001e\u000f\u0000\u01ed\u01ee\u0005\b\u0000\u0000\u01ee\u01fe\u0001"+
		"\u0000\u0000\u0000\u01ef\u01f0\n\u0004\u0000\u0000\u01f0\u01f1\u0005X"+
		"\u0000\u0000\u01f1\u01f2\u0005`\u0000\u0000\u01f2\u01f3\u0005-\u0000\u0000"+
		"\u01f3\u01f4\u0005`\u0000\u0000\u01f4\u01fe\u0005\b\u0000\u0000\u01f5"+
		"\u01f6\n\u0003\u0000\u0000\u01f6\u01fe\u0005Y\u0000\u0000\u01f7\u01f8"+
		"\n\u0002\u0000\u0000\u01f8\u01fe\u0005Z\u0000\u0000\u01f9\u01fa\n\u0001"+
		"\u0000\u0000\u01fa\u01fb\u0005[\u0000\u0000\u01fb\u01fc\u0005`\u0000\u0000"+
		"\u01fc\u01fe\u0005\b\u0000\u0000\u01fd\u01ea\u0001\u0000\u0000\u0000\u01fd"+
		"\u01ef\u0001\u0000\u0000\u0000\u01fd\u01f5\u0001\u0000\u0000\u0000\u01fd"+
		"\u01f7\u0001\u0000\u0000\u0000\u01fd\u01f9\u0001\u0000\u0000\u0000\u01fe"+
		"\u0201\u0001\u0000\u0000\u0000\u01ff\u01fd\u0001\u0000\u0000\u0000\u01ff"+
		"\u0200\u0001\u0000\u0000\u0000\u0200\u001f\u0001\u0000\u0000\u0000\u0201"+
		"\u01ff\u0001\u0000\u0000\u0000\u0202\u0207\u0005`\u0000\u0000\u0203\u0207"+
		"\u0005a\u0000\u0000\u0204\u0207\u0005c\u0000\u0000\u0205\u0207\u0005b"+
		"\u0000\u0000\u0206\u0202\u0001\u0000\u0000\u0000\u0206\u0203\u0001\u0000"+
		"\u0000\u0000\u0206\u0204\u0001\u0000\u0000\u0000\u0206\u0205\u0001\u0000"+
		"\u0000\u0000\u0207!\u0001\u0000\u0000\u0000\u0208\u020d\u0003$\u0012\u0000"+
		"\u0209\u020a\u0005-\u0000\u0000\u020a\u020c\u0003$\u0012\u0000\u020b\u0209"+
		"\u0001\u0000\u0000\u0000\u020c\u020f\u0001\u0000\u0000\u0000\u020d\u020b"+
		"\u0001\u0000\u0000\u0000\u020d\u020e\u0001\u0000\u0000\u0000\u020e#\u0001"+
		"\u0000\u0000\u0000\u020f\u020d\u0001\u0000\u0000\u0000\u0210\u0213\u0005"+
		"i\u0000\u0000\u0211\u0212\u0005>\u0000\u0000\u0212\u0214\u0005i\u0000"+
		"\u0000\u0213\u0211\u0001\u0000\u0000\u0000\u0213\u0214\u0001\u0000\u0000"+
		"\u0000\u0214%\u0001\u0000\u0000\u0000\u0215\u0216\u0005i\u0000\u0000\u0216"+
		"\'\u0001\u0000\u0000\u0000\u0217\u0218\u0005i\u0000\u0000\u0218)\u0001"+
		"\u0000\u0000\u0000\u0219\u021a\u0005i\u0000\u0000\u021a+\u0001\u0000\u0000"+
		"\u0000\u021b\u021c\u0005i\u0000\u0000\u021c-\u0001\u0000\u0000\u0000\u001e"+
		"=EG^u\u0097\u0099\u00e1\u00e8\u0108\u010a\u0135\u013f\u0147\u0151\u0155"+
		"\u018e\u019e\u01a3\u01cc\u01ce\u01d4\u01db\u01e3\u01e8\u01fd\u01ff\u0206"+
		"\u020d\u0213";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}