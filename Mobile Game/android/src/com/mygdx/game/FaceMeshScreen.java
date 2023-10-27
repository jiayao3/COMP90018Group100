package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.google.common.collect.ImmutableSet;
import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.solutions.facemesh.FaceMeshConnections;
import com.google.mediapipe.solutions.facemesh.FaceMeshResult;

import java.util.List;

public class FaceMeshScreen implements Screen {
    private ShapeRenderer shapeRenderer;

    private FaceMeshResult faceMeshResult;  // Assume this is set elsewhere or passed in.

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render FaceMeshResult
        if(faceMeshResult != null) {
            shapeRenderer.begin(ShapeType.Line);
            // TODO: Use shapeRenderer to draw your face mesh lines. You might loop over the connections similar to your provided code.
            shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    // ... other screen methods ...

    private void drawLandmarks(
            List<LandmarkProto.NormalizedLandmark> faceLandmarkList,
            ImmutableSet<FaceMeshConnections.Connection> connections) {
        for (FaceMeshConnections.Connection c : connections) {
            LandmarkProto.NormalizedLandmark start = faceLandmarkList.get(c.start());
            LandmarkProto.NormalizedLandmark end = faceLandmarkList.get(c.end());

            // Convert from normalized coordinates to screen coordinates if needed
            float startX = start.getX() * Gdx.graphics.getWidth();
            float startY = (1 - start.getY()) * Gdx.graphics.getHeight(); // y is flipped in LibGDX
            float endX = end.getX() * Gdx.graphics.getWidth();
            float endY = (1 - end.getY()) * Gdx.graphics.getHeight();

            // Draw using shapeRenderer
            shapeRenderer.line(startX, startY, endX, endY);
        }
    }

    // Implement other methods as needed.
}
