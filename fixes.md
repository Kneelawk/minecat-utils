# Fixes

This is a list of the fixes provided by the fixes module(s):

1. Minecraft will sometimes crash on less-powerful computers during resource-loading with a
   `ConcurrentModificationException`.
    * This was fixed by replacing the `PreparableReloadListener` list with a custom implementation that is more
      thread-safe.
2. Players advancements seem to be causing lag???
    * This was fixed by replacing the implementation of `HashSet` with a `LinkedHashSet` so that player-listeners can be
      iterated over faster.
3. Having more than 100 HUD layers while also having Xaero's Minimap installed causes vanilla HUD elements to begin to
   disappear. The most noticeable is the vanilla chat-bar on the chat screen.
    * This was fixed by capping the value returned by `ClientHooks.getGuiFarPlane()` to 31000 when setting up the
      model-view matrix in the `GameRenderer`.
    * An effort was also made to prevent modded HUD items from falling off the GUI planes.
    * This fix is no longer needed, as an update in Xaero's Minimap has fixed the issue.
