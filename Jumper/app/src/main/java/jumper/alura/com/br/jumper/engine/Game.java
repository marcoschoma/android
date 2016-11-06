package jumper.alura.com.br.jumper.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jumper.alura.com.br.graphic.Tela;
import jumper.alura.com.br.jumper.elements.Cano;
import jumper.alura.com.br.jumper.elements.FimJogo;
import jumper.alura.com.br.jumper.elements.Fundo;
import jumper.alura.com.br.jumper.elements.Passaro;
import jumper.alura.com.br.jumper.elements.Pontuacao;
import jumper.alura.com.br.jumper.elements.TituloJogo;

/**
 * Created by marco on 27/08/2016.
 */
public class Game extends SurfaceView implements Runnable, View.OnTouchListener{

    private Context context;
    private boolean isRunning = false;

    private byte gameStatus;
    private byte STATUS_RUNNING = 0, STATUS_GAMEOVER = 1, STATUS_MAINSCREEN = 2;

    private SurfaceHolder holder;
    private TituloJogo tituloJogo;
    private Passaro passaro;
    private Fundo fundo;
    private Tela tela;

    private List<Cano[]> canos;

    private final float posicialInicialCanoCima = 0;
    private final int NUM_CANOS = 3;

    private float distanciaEntreCanos;
    private float gapMinimo;

    private List<Float> posicoesCano;

    private Pontuacao pontos;

    private int duracaoMinimaFrameEmMiliseg = 100;

    public Game(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();

        posicoesCano = new ArrayList<>();

        inicializaElementos();
        setOnTouchListener(this);
        gameStatus = STATUS_MAINSCREEN;
    }

    private void inicializaElementos() {
        tela = new Tela(this.context);
        tituloJogo = new TituloJogo(tela);

        passaro = new Passaro(getResources(), tela.getAltura() / 2);
        fundo = new Fundo(getResources(), tela.getLargura(), tela.getAltura());
        pontos = new Pontuacao();
        distanciaEntreCanos = 1000; //tela.getLargura();
        calcularDificuldade();
        gapMinimo = passaro.ALTURA * 6;

        canos = new ArrayList<Cano[]>();
        for (int i = 0; i < NUM_CANOS; i++) {
            canos.add(criaNovoCano());
        }
        Cano.iniciarVelocidadeCanos();
    }

    @Override
    public void run() {
        while (isRunning) {

            if(gameStatus == STATUS_RUNNING) {
                if(testaFimDoJogo()) {
                    gameStatus = STATUS_GAMEOVER;
                    passaro.para();
                    desabilitaToquePor(2000);
                } else {
                    atualizaElementos();
                    atualizaQuadro();
                }
            }
            atualizaInterface(quadroAtual);
        }
    }

    private long ultimaAtualizacaoQuadro = 0;
    private int quadroAtual = 0, quadroMax = 8;

    public void atualizaQuadro() {
        long time = System.currentTimeMillis();
        if(time > ultimaAtualizacaoQuadro + duracaoMinimaFrameEmMiliseg) {
            ultimaAtualizacaoQuadro = time;
            quadroAtual++;
            if (quadroAtual > quadroMax)
                quadroAtual = 0;
        }
    }

    private void atualizaInterface(int quadro) {
        if(holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            fundo.desenhaNoCanvas(canvas);
            passaro.desenhaNoCanvas(canvas, quadro);

            if(gameStatus == STATUS_MAINSCREEN) {
                tituloJogo.desenha(canvas);
            }

            if(gameStatus == STATUS_RUNNING || gameStatus == STATUS_GAMEOVER) {
                for (int i = 0; i < canos.size(); i++) {
                    Cano[] tmpCanos = canos.get(i);
                    if (tmpCanos != null) {
                        tmpCanos[0].desenhaNo(canvas);
                        tmpCanos[1].desenhaNo(canvas);
                    }
                }
                pontos.desenha(canvas);
            }

            if(gameStatus == STATUS_GAMEOVER) {
                FimJogo fimJogo = new FimJogo();
                fimJogo.DesenhaFimJogo(canvas, pontos.getPontos());
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void atualizaElementos() {
        passaro.move();
        for (int i = 0; i < canos.size(); i++) {
            Cano[] tmpCanos = canos.get(i);
            if (tmpCanos != null) {
                Cano canoCima = tmpCanos[0];
                canoCima.anda();
                tmpCanos[1].anda();

                if(canoCima.getPosicaoLateral() + canoCima.LARGURA < 0) {
                    canos.set(i, null);
                    canos.add(criaNovoCano());
                    pontos.aumenta();
                    calcularDificuldade();
                }
            }
        }
    }

    private boolean testaFimDoJogo() {
        if(passaro.getPosicaoVertical() + passaro.ALTURA > tela.getAltura()) {
            return true;
        } else if (passaro.getPosicaoVertical() < 0) {
            return true;
        } else if (testaColisaoCanoPassaro(passaro, canos)) {
            return true;
        }
        return false;
    }

    private boolean testaColisaoCanoPassaro(Passaro passaro, List<Cano[]> canos) {
        if(canos == null)
            return false;

        for (int i = 0; i < canos.size(); i++) {
            Cano[] cano = canos.get(i);
            if(cano != null && (cano[0].temColisao(passaro) || cano[1].temColisao(passaro)))
                return true;
        }
        return false;
    }

    private void calcularDificuldade() {
        float variacaoDificuldade = pontos.getPontos() / 5;
        distanciaEntreCanos -= variacaoDificuldade;
        gapMinimo -= variacaoDificuldade;
        Cano.aumentarVelocidadeCanos((int)variacaoDificuldade);
    }

    private float calculaPosicaoLateralCano(){
        if(!canos.isEmpty()) {
            return canos.get(canos.size() - 1)[0].getPosicaoLateral() + distanciaEntreCanos;
        } else {
            return distanciaEntreCanos;
        }
    }

    private Cano[] criaNovoCano() {
        Cano canoCima, canoBaixo;
        float random = Math.abs((float)Math.random() * (tela.getAltura() * 0.7f));

        float posicaolLateralCano = calculaPosicaoLateralCano();
        posicoesCano.add(posicaolLateralCano);

        canoCima = new Cano(tela, random, posicaolLateralCano, posicialInicialCanoCima, true);
        canoBaixo = new Cano(tela, tela.getAltura() - random - gapMinimo, posicaolLateralCano, random + gapMinimo, false);

        return new Cano[] { canoCima, canoBaixo };
    }

    public void inicia() {
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(isToqueHabilitado()) {
            if (gameStatus == STATUS_RUNNING) {
                passaro.pula();
            } else if (gameStatus == STATUS_GAMEOVER || gameStatus == STATUS_MAINSCREEN) {
                inicializaElementos();
                gameStatus = STATUS_RUNNING;
                passaro.cai();
            }
        }
        return false;
    }

    long ultimaInterrupcaoToque = 0;
    private boolean isToqueHabilitado() {
        return System.currentTimeMillis() > ultimaInterrupcaoToque;
    }

    private void desabilitaToquePor(long milisegundos) {
        ultimaInterrupcaoToque = System.currentTimeMillis() + milisegundos;
    }
}
